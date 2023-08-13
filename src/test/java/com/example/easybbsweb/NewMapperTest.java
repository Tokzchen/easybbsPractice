package com.example.easybbsweb;

import com.example.easybbsweb.domain.entity.AidProcess;
import com.example.easybbsweb.domain.entity.LawAid;
import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.others.lawAid.UniversityPair;
import com.example.easybbsweb.mapper.AidProcessMapper;
import com.example.easybbsweb.mapper.LawAidMapper;
import com.example.easybbsweb.mapper.UniversityMapper;
import com.example.easybbsweb.service.UniversityService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class NewMapperTest {
    @Resource
    AidProcessMapper aidProcessMapper;
    @Resource
    LawAidMapper lawAidMapper;

    @Resource
    UniversityMapper universityMapper;

    @Resource
    UniversityService universityService;


    @Test
    void func1(){
        List<AidProcess> aidProcesses = aidProcessMapper.selectLatelyUniversity(7);
        System.out.println(aidProcesses);
    }

    @Test
    void func2(){
        List<LawAid> lawAids = lawAidMapper.selectLatelyUniversity(7);
        System.out.println(lawAids);
    }


    @Test
    void func3(){
        UniversityPair u1 = new UniversityPair();
        UniversityPair u2 = new UniversityPair();
        UniversityPair u3 = new UniversityPair();
        u1.setUniId((long)11122);
        u2.setUniId((long)11236);
        u3.setUniId((long)11121);
        ArrayList<UniversityPair> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);

        List<University> rlist = universityService.getUniversityInfoByUniIdList(list);
        System.out.println("查询得到的数目"+rlist.size());
        rlist.forEach(r->{
            System.out.println(r);
        });
    }

}
