package com.example.easybbsweb.service.impl;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceWrapper;
import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.example.easybbsweb.domain.entity.*;
import com.example.easybbsweb.domain.others.LawAidInfoPageUser;
import com.example.easybbsweb.domain.others.lawAid.UniversityPair;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.SystemException;
import com.example.easybbsweb.mapper.*;
import com.example.easybbsweb.service.IbsService;
import com.example.easybbsweb.service.LawAidService;
import com.example.easybbsweb.service.SurveyService;
import com.example.easybbsweb.utils.RedisUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class LawAidServiceImpl implements LawAidService {
    private static final Integer MAX_NEARBY_UNIVERSITY_DISTANCE=100;
    private static final Integer MAX_RECOMMEND_UNIVERSITY_CNT=20;

    private static final Integer LOW_ACTIVE_STANDARD_CNT=10;
    private static final Integer LOW_ACTIVE_UPDATE_HOURS=48;

    private static final Integer MED_ACTIVE_STANDARD_CNT=25;
    private static final Integer MED_ACTIVE_UPDATE_HOURS=24;

    private static final Integer HIGH_ACTIVE_UPDATE_HOURS=12;


    @Resource
    UniversityMapper universityMapper;
    @Resource
    UserMainMapper userMainMapper;

    @Resource
    LawAidMapper lawAidMapper;

    @Resource
    AidProcessMapper aidProcessMapper;
    @Resource
    IbsService ibsService;

    @Resource
            //这里有个bug这个版本的druid注入Datesource失败，只能注入这个类
    DruidDataSourceWrapper datasource;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    SurveyService surveyService;


    @Override
    public boolean saveDocumentPath(University university) {
        //首先对university进行参数校验
        if(university.getFile()==null||university.getFile().equals("")){
            throw new BusinessException("存储路径不得为空");
        }
        if(university.getUniId()==null){
            throw new BusinessException("存储对象暂时未登记");
        }

        //重新生成一个对象，将其他的数据置空
        University insertData = new University();
        insertData.setUniId(university.getUniId());
        insertData.setFile(university.getFile());

        UniversityExample universityExample = new UniversityExample();
        universityExample.createCriteria().andUniIdEqualTo(insertData.getUniId());
        int i = universityMapper.updateByExampleSelective( insertData,universityExample);

        return i>0;

    }

    @Override
    public LawAidInfoPageUser getUserLawAidInfo(Long userId) {
        //查用户缓存
        LawAidInfoPageUser cache = (LawAidInfoPageUser) RedisUtils.get(userId + ":lawAidInfo");
        if(cache!=null){
            return cache;
        }
        //获取1.求助领域，2.关联大学 3.总共求助次数 4.当前求助内容的进度
        LawAidInfoPageUser lawAidInfoPageUser = new LawAidInfoPageUser();
        //1.获取求助领域，如还没选择则显示 尚未选择
        UserMainExample userMainExample = new UserMainExample();
        userMainExample.createCriteria().andUserIdEqualTo(userId);
        List<UserMain> userMains = userMainMapper.selectByExample(userMainExample);
        if(userMains.size()==0){
            UserMain userMain = new UserMain();
            userMain.setUserId(userId);
            userMain.setCreateTime(new Date());
            userMainMapper.insertSelective(userMain);
        }
        lawAidInfoPageUser.setCurrentArea(userMains.size()!=0?userMains.get(0).getArea():null);
        //2.获取关联的大学
        if(userMains.size()>0&&userMains.get(0).getUniId()!=null){
            University university = universityMapper.selectByPrimaryKey(userMains.get(0).getUniId());
            lawAidInfoPageUser.setUniversity(university);
        }else{
            lawAidInfoPageUser.setUniversity(null);
        }


        //3.总共求助的次数，即正式达成lawAid订单的次数
        LawAidExample lawAidExample = new LawAidExample();
        lawAidExample.createCriteria().andUserIdEqualTo(userId);
        List<LawAid> lawAids = lawAidMapper.selectByExample(lawAidExample);
        lawAidInfoPageUser.setLawAidCnt(lawAids.size());

        //4.当前求助的进度信息
        //查出当前求助的lawAid订单->根据订单信息查找该订单的所有进度信息
        //如果没有求助信息就直接返回
        if(lawAids.size()==0){
            return lawAidInfoPageUser;
        }
        lawAids.sort(new Comparator<LawAid>() {
            @Override
            public int compare(LawAid o1, LawAid o2) {
                //这里调整升降序，o2->o1,此时为降序
                return o2.getCreateTime().toInstant().compareTo(o1.getCreateTime().toInstant());
            }
        });

        //获取第一个lawAid(最近一个lawAid)订单
        LawAid lawAid = lawAids.get(0);
        //根据该lawAid订单去查询aidProcess记录
        AidProcessExample aidProcessExample = new AidProcessExample();
        aidProcessExample.createCriteria().andLawAidIdEqualTo(lawAid.getLawAidId());
        List<AidProcess> aidProcesses = aidProcessMapper.selectByExample(aidProcessExample);
        //对进度内容进行调整,降序
        aidProcesses.sort(new Comparator<AidProcess>() {
            @Override
            public int compare(AidProcess o1, AidProcess o2) {
                return o2.getCreateTime().toInstant().compareTo(o1.getCreateTime().toInstant());

            }
        });
        lawAidInfoPageUser.setProgress(aidProcesses);
        RedisUtils.set(userId+":lawAidInfo",lawAidInfoPageUser);
        return lawAidInfoPageUser;


    }

    protected synchronized boolean checkAndAddIfAbsent(Long uniId,Integer deltaCnt,Vector<UniversityPair> list){
        for(int i=0;i<list.size();i++){
            UniversityPair universityPair = list.get(i);
            if(universityPair.getUniId().equals(uniId)){
                int pre = universityPair.getCnt().intValue();
                universityPair.getCnt().compareAndSet(pre,pre+deltaCnt);
                return true;
            }
        }
        list.add(new UniversityPair(uniId,new AtomicInteger(deltaCnt),null));
        return true;

    }

    @Override
    public List<UniversityPair> generateAndRecommendUniversities(Long userId, Point point,String area) {
        //使用vector进行处理，单个方法线程安全，UniversityPair.cnt是AtomicInteger
        Vector<UniversityPair> universityList = new Vector<>();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        threadPoolTaskExecutor.submit(new Thread(()->{
            generateIndexOneForUser(userId,point,universityList);
            countDownLatch.countDown();
        }));
        threadPoolTaskExecutor.submit(new Thread(() -> {
            generateIndexTwoForUser(universityList);
            countDownLatch.countDown();
        }));

        threadPoolTaskExecutor.submit(new Thread(()->{
            generateIndexThreeForUser(universityList,area);
            countDownLatch.countDown();
        }));

        try {
            countDownLatch.await(); // 等待所有线程完成
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        //对universityList进行排序处理
        universityList.sort(new Comparator<UniversityPair>() {
            @Override
            public int compare(UniversityPair o1, UniversityPair o2) {
                return o1.getCnt().intValue()-o2.getCnt().intValue();
            }
        });
        return universityList;
    }

   //产生推荐业务的第一个指标:位置信息
    public void generateIndexOneForUser(Long userId,Point point,Vector<UniversityPair> list){
        //获取用户附近的大学位置信息
        GeoResults<RedisGeoCommands.GeoLocation<Object>> nearbyUniversityInfo = ibsService.getNearbyUniversityInfo(point, MAX_NEARBY_UNIVERSITY_DISTANCE, MAX_RECOMMEND_UNIVERSITY_CNT);
        //平均距离
        Distance averageDistance = nearbyUniversityInfo.getAverageDistance();
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = nearbyUniversityInfo.getContent();
        Integer totalPoint=100;
        if(content.size()==0){
            return;
        }
        Integer decrePerUni=totalPoint/content.size();
        for(int i=0;i<content.size();i++){
            GeoResult<RedisGeoCommands.GeoLocation<Object>> geoLocationGeoResult = content.get(i);
            Distance distance = geoLocationGeoResult.getDistance();
            System.out.println(geoLocationGeoResult.getContent().getName());
            Long uniId = Long.valueOf((String) geoLocationGeoResult.getContent().getName());
            Distance universityDistance = geoLocationGeoResult.getDistance();

            //以下是并发逻辑处理
            //checkAndAddIfAbsent通过锁判断是否存在，且不存在会添加
            checkAndAddIfAbsent(uniId, totalPoint - (i * decrePerUni), list);
        }
    }
    //产生第二个指标-近期的订单量(活跃度)，这个指标可以使用缓存进行处理
    //缓存过期时间策略:监控lawAid订单表，根据插入频率动态调整
    public void generateIndexTwoForUser(Vector<UniversityPair> list){
        List<UniversityPair> activenessOfUniversity = (List<UniversityPair>) RedisUtils.get("lawAid:index2");
        if(activenessOfUniversity==null){
            //缓存过期
            //首先通过druid的sql监控查看数据库的更新状态
            //上一次缓存的更新时间
            int updateCnt = getUniversityActiveNess();
            //获取活跃度前20的高校
             activenessOfUniversity = generateIndexTwoForUserMainLogic();
            //根据updateCnt进行缓存过期时间的设置
            int hours = updateCntRedisLife(updateCnt);
            RedisUtils.set("lawAid:index2",activenessOfUniversity,60*60*hours);
        }
        if(activenessOfUniversity.size()==0){
            return;
        }
        int totalCnt=80;
        int decrePerUni=totalCnt/activenessOfUniversity.size();
        log.info("更新缓存活跃高校列表为{}",activenessOfUniversity);
        for(int i=0;i<activenessOfUniversity.size();i++){
            boolean b = checkAndAddIfAbsent(activenessOfUniversity.get(i).getUniId(),
                    totalCnt - (i * decrePerUni),
                    list);
        }

    }

    public int getUniversityActiveNess(){
        String[] relatedSqlList={
                "insert into law_aid",
                "insert into aid_process"
        };
        Integer updateCnt=0;
        Date lastUpdateTime = (Date) RedisUtils.get("lawAid:index2:lastUpdateTime");
        RedisUtils.setPersistently("lawAid:index2:lastUpdateTime",new Date());
        List<Map<String, Object>> sqlStatDataList = DruidStatManagerFacade.getInstance().getSqlStatDataList(datasource);
        for(Map<String,Object> map:sqlStatDataList){
            String sql = (String) map.get("SQL");
            log.info("sql语句:{}",sql);
            Date maxTimespanOccurTime = (Date) map.get("MaxTimespanOccurTime");
            if(maxTimespanOccurTime.after(lastUpdateTime)&&isContains(relatedSqlList,sql)){
                updateCnt++;
            }
        }
        return updateCnt;
    }

    protected int checkAndAddIfAbsentEasy(List<UniversityPair> list,Long uniId,Integer delta){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getUniId().equals(uniId)){
                return i;
            }
        }
        UniversityPair universityPair = new UniversityPair();
        universityPair.setUniId(uniId);
        universityPair.setEasyCnt(delta);
        list.add(universityPair);
        return -1;
    }
//法律援助第二个指标量的主逻辑:进行查表获取aid_process以及lawAid订单量，进行
    protected List<UniversityPair> generateIndexTwoForUserMainLogic(){
        ArrayList<UniversityPair> universityPairs = new ArrayList<>();
        List<LawAid> lawAids = lawAidMapper.selectLatelyUniversity(30);
        List<AidProcess> aidProcesses = aidProcessMapper.selectLatelyUniversity(30);
        if(lawAids.size()>0){
            int totalSize1=80;
            int decrePerUni=totalSize1/lawAids.size();
            for(int i=0;i<lawAids.size();i++){
                int exist = checkAndAddIfAbsentEasy(universityPairs, lawAids.get(i).getUniId(), totalSize1 - (i * decrePerUni));
                if(exist>-1){
                    Integer easyCnt = universityPairs.get(exist).getEasyCnt();
                    universityPairs.get(exist).setEasyCnt(easyCnt+totalSize1 - (i * decrePerUni));
                }
            }

        }
        if(aidProcesses.size()>0){
            int totalSize2=65;
            int decrePerUni1=totalSize2/aidProcesses.size();
            for(int i=0;i<aidProcesses.size();i++){
                int exist = checkAndAddIfAbsentEasy(universityPairs, aidProcesses.get(i).getUniId(), totalSize2 - (i * decrePerUni1));
                if(exist>-1){
                    Integer easyCnt = universityPairs.get(exist).getEasyCnt();
                    universityPairs.get(exist).setEasyCnt(easyCnt+totalSize2 - (i * decrePerUni1));
                }
            }

        }
        //对pair进行排序
        universityPairs.sort(new Comparator<UniversityPair>() {
            @Override
            public int compare(UniversityPair o1, UniversityPair o2) {
                return o1.getEasyCnt()-o2.getEasyCnt();
            }
        });

        return universityPairs.size()>MAX_RECOMMEND_UNIVERSITY_CNT
                ?
                universityPairs.subList(0,19)
                :universityPairs;
    }

    protected boolean isContains(String[] sqlList,String sql){
        for(String rsql:sqlList){
            String prefix = rsql.toLowerCase().replaceAll(" ", "");
            String target = sql.toLowerCase().replaceAll(" ", "");
            if(target.startsWith(prefix)){
                return true;
            }
        }
        return false;
    }


    public void generateIndexThreeForUser(Vector<UniversityPair> list,String area){
        //指标三，查询高校历史记录中该领域的单量
        List<Answer> surveyArea = surveyService.getSurveyArea();
        boolean exists = surveyArea.stream().anyMatch(answer -> answer.getContent().equals(area));
        if(!exists){
            throw new BusinessException("援助业务不存在");
        }
        //查看缓存
        List<LawAid> sortedList = (List<LawAid>) RedisUtils.get("lawAid:index3:" + area);
        if(sortedList==null){
            //该领域过期代表全都过期啦，进行缓存更新
            //近期高校活跃热度
            int universityActiveNess = getUniversityActiveNess();
            int hours = updateCntRedisLife(universityActiveNess);
            for(Answer answer:surveyArea){
                //一一进行查找并且缓存好
                List<LawAid> lawAids = lawAidMapper.selectSpecificUniCnt(answer.getContent());
                RedisUtils.set("lawAid:index3:" + answer.getContent(), lawAids, 60 * 60 * hours);
            }
            //从新缓存中再获取
            sortedList = (List<LawAid>) RedisUtils.get("lawAid:index3:" + area);
            generateIndex3MainLogic(list,sortedList);
        }else{
            generateIndex3MainLogic(list,sortedList);
        }


    }

    protected void generateIndex3MainLogic(Vector<UniversityPair> list,List<LawAid> orderedList){
        if(orderedList.size()==0){
            return;
        }else{
            int totalCnt=40;
            int decrePerUni=40/orderedList.size();
            for(int i=0;i<orderedList.size();i++){
               checkAndAddIfAbsent(orderedList.get(i).getUniId(), totalCnt - (i * decrePerUni), list);
            }
        }
    }

    protected int updateCntRedisLife(int updateCnt){
        if(updateCnt<=LOW_ACTIVE_STANDARD_CNT){
            return LOW_ACTIVE_UPDATE_HOURS;
        }else if(updateCnt>LOW_ACTIVE_STANDARD_CNT&&updateCnt<MED_ACTIVE_STANDARD_CNT){
          return MED_ACTIVE_UPDATE_HOURS;
        }else if(updateCnt>MED_ACTIVE_STANDARD_CNT){
           return HIGH_ACTIVE_UPDATE_HOURS;
        }else{
            throw new SystemException("区间设置不合法导致错误");
        }
    }


}
