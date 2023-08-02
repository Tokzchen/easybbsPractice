package com.example.easybbsweb;

import com.example.easybbsweb.utils.Ip2RegionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Ip2RegionTest {

    @Test
    void func1(){
        String cityInfo = Ip2RegionUtil.getCityInfo("220.248.12.158");
        System.out.println(cityInfo);
    }
}
