package com.example.easybbsweb;

import com.example.easybbsweb.domain.entity.*;
import com.example.easybbsweb.domain.entity.dto.UserDTO1;
import com.example.easybbsweb.domain.others.lawAid.LawAidInfoPageUni;
import com.example.easybbsweb.domain.others.lawAid.UniversityPair;
import com.example.easybbsweb.mapper.*;
import com.example.easybbsweb.service.LawAidService;
import com.example.easybbsweb.service.UniversityService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewMapperTest {
    @Resource
    AidProcessMapper aidProcessMapper;
    @Resource
    LawAidMapper lawAidMapper;

    @Resource
    UniversityMapper universityMapper;

    @Resource
    UniversityService universityService;

    @Resource
    UserInfoMapper userInfoMapper;

    @Resource
    LawAidService lawAidService;

    @Resource
    YoufaMailMapper youfaMailMapper;


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

    @Test
    void func4(){
        LawAidExample lawAidExample = new LawAidExample();
        lawAidExample.createCriteria()
                .andUserIdEqualTo(11235L)
                .andUniIdEqualTo(11121L)
                .andStateNotEqualTo("2");//结束状态
        List<LawAid> lawAids = lawAidMapper.selectByExample(lawAidExample);
        System.out.println(lawAids);
    }


    @Test
    void func5(){
        LawAidInfoPageUni uniLawAidInfo = lawAidService.getUniLawAidInfo(11121);
        System.out.println(uniLawAidInfo);

    }

    @Test
    void func6(){
        List<YoufaMail> userMails = youfaMailMapper.getUserMails(11235l);
        System.out.println(userMails);
    }

}
