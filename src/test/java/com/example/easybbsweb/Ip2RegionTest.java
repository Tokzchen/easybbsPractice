package com.example.easybbsweb;

import com.example.easybbsweb.domain.others.lawAid.UniversityPair;
import com.example.easybbsweb.utils.Ip2RegionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Vector;

@SpringBootTest
public class Ip2RegionTest {
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    void func1(){
        String cityInfo = Ip2RegionUtil.getCityInfo("220.248.12.158");
        System.out.println(cityInfo);
    }

    @Test
    void func2(){
        Vector<UniversityPair> universityPairs = new Vector<>();
        threadPoolTaskExecutor.submit(new Thread(()->{
            //will use pairs
        }));
        threadPoolTaskExecutor.submit(new Thread(()->{
            //will use pairs
        }));

    }
}
