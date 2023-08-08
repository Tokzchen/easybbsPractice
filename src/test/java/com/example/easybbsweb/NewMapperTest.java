package com.example.easybbsweb;

import com.example.easybbsweb.domain.entity.AidProcess;
import com.example.easybbsweb.domain.entity.LawAid;
import com.example.easybbsweb.mapper.AidProcessMapper;
import com.example.easybbsweb.mapper.LawAidMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class NewMapperTest {
    @Resource
    AidProcessMapper aidProcessMapper;
    @Resource
    LawAidMapper lawAidMapper;


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
}
