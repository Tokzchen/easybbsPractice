package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.*;
import com.example.easybbsweb.domain.others.LawAidInfoPageUser;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.mapper.AidProcessMapper;
import com.example.easybbsweb.mapper.LawAidMapper;
import com.example.easybbsweb.mapper.UniversityMapper;
import com.example.easybbsweb.mapper.UserMainMapper;
import com.example.easybbsweb.service.LawAidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;

@Service
public class LawAidServiceImpl implements LawAidService {

    @Autowired
    UniversityMapper universityMapper;
    @Autowired
    UserMainMapper userMainMapper;

    @Autowired
    LawAidMapper lawAidMapper;

    @Autowired
    AidProcessMapper aidProcessMapper;

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
        //1.获取求助领域，如还没选择则显示 未选择
        UserMainExample userMainExample = new UserMainExample();
        userMainExample.createCriteria().andUserIdEqualTo(userId);
        List<UserMain> userMains = userMainMapper.selectByExample(userMainExample);
        lawAidInfoPageUser.setCurrentArea(StringUtils.hasText(userMains.get(0).getArea())?userMains.get(0).getArea():"未选择");
        //2.获取关联的大学
        if(userMains.get(0).getUniId()!=null){
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
}
