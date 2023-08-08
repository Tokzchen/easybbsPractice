package com.example.easybbsweb.service.impl;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceWrapper;
import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.example.easybbsweb.domain.entity.*;
import com.example.easybbsweb.domain.others.LawAidInfoPageUser;
import com.example.easybbsweb.domain.others.lawAid.UniversityPair;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.mapper.AidProcessMapper;
import com.example.easybbsweb.mapper.LawAidMapper;
import com.example.easybbsweb.mapper.UniversityMapper;
import com.example.easybbsweb.mapper.UserMainMapper;
import com.example.easybbsweb.service.IbsService;
import com.example.easybbsweb.service.LawAidService;
import com.example.easybbsweb.utils.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LawAidServiceImpl implements LawAidService {
    private static final Integer MAX_NEARBY_UNIVERSITY_DISTANCE=100;
    private static final Integer MAX_RECOMMEND_UNIVERSITY_CNT=20;

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


        return lawAidInfoPageUser;


    }

    protected synchronized Integer checkAndAddIfAbsent(Long uniId,Integer deltaCnt,Vector<UniversityPair> list){
        for(int i=0;i<list.size();i++){
            UniversityPair universityPair = list.get(i);
            if(universityPair.getUniId().equals(uniId)){
                return i;
            }
        }
        list.add(new UniversityPair(uniId,new AtomicInteger(deltaCnt)));
        return -1;

    }

    @Override
    public List<University> generateAndRecommendUniversities(Long userId, Point point) {
        //使用vector进行处理，单个方法线程安全，UniversityPair.cnt是AtomicInteger
        Vector<UniversityPair> universityList = new Vector<>();
        generateIndexOneForUser(userId,point,universityList);
        return null;
    }

   //产生推荐业务的第一个指标:位置信息
    protected void generateIndexOneForUser(Long userId,Point point,Vector<UniversityPair> list){
        //获取用户附近的大学位置信息
        GeoResults<RedisGeoCommands.GeoLocation<Object>> nearbyUniversityInfo = ibsService.getNearbyUniversityInfo(point, MAX_NEARBY_UNIVERSITY_DISTANCE, MAX_RECOMMEND_UNIVERSITY_CNT);
        //平均距离
        Distance averageDistance = nearbyUniversityInfo.getAverageDistance();
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = nearbyUniversityInfo.getContent();
        Integer totalPoint=100;
        Integer decrePerUni=totalPoint/content.size();
        for(int i=0;i<content.size();i++){
            GeoResult<RedisGeoCommands.GeoLocation<Object>> geoLocationGeoResult = content.get(i);
            Distance distance = geoLocationGeoResult.getDistance();
            Long uniId = (Long) geoLocationGeoResult.getContent().getName();
            Distance universityDistance = geoLocationGeoResult.getDistance();

            //以下是并发逻辑处理
            //checkAndAddIfAbsent通过锁判断是否存在，且不存在会添加
            Integer index = checkAndAddIfAbsent(uniId, totalPoint - (i * decrePerUni), list);
            if(index>=0){
                //如果存在，则进行进一步的处理
                UniversityPair universityPair = list.get(index);
                int pre = universityPair.getCnt().intValue();
                int newValue=totalPoint-(i*decrePerUni);
                //原子类AtomicInteger，通过乐观锁CAS实现了原子操作
                while (universityPair.getCnt().compareAndSet(pre, newValue)){
                    universityPair.getCnt().compareAndSet(pre, newValue);
                }
                }else{
                //不存在则该步已经完成
                continue;
            }
        }
    }
    //产生第二个指标-近期的订单量，这个指标可以使用缓存进行处理
    //缓存过期时间策略:监控lawAid订单表，根据插入频率动态调整
    protected void generateIndexTwoForUser(Long userId){
        Object o = RedisUtils.get("lawAid:index2");
        if(o==null){
            //缓存过期
            //首先通过druid的sql监控查看数据库的更新状态
            //上一次缓存的更新时间
            String[] relatedSqlList={
                    "insert into law_aid",
                    "insert into aid_process"
            };
            Integer updateCnt=0;
            Date lastUpdateTime = (Date) RedisUtils.get("lawAid:index2:lastUpdateTime");
            List<Map<String, Object>> sqlStatDataList = DruidStatManagerFacade.getInstance().getSqlStatDataList(datasource);
            for(Map<String,Object> map:sqlStatDataList){
                String sql = (String) map.get("SQL");
                Date maxTimespanOccurTime = (Date) map.get("MaxTimespanOccurTime");
                if(maxTimespanOccurTime.after(lastUpdateTime)&&isContains(relatedSqlList,sql)){
                    updateCnt++;
                }
            }
            //根据updateCnt进行不同的缓存时长设置->

        }

    }
//法律援助第二个指标量的主逻辑:进行查表获取aid_process以及lawAid订单量，进行
    protected List<UniversityPair> generateIndexTwoForUserMainLogic(){
        ArrayList<University> universities = new ArrayList<>();
        List<AidProcess> aidProcesses = aidProcessMapper.selectLatelyUniversity(30);
        List<LawAid> lawAids = lawAidMapper.selectLatelyUniversity(30);
        //最近30天的活跃院校
        int i=0;
        for(AidProcess a:aidProcesses){
            University university = new University();

        }

        return null;
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


}
