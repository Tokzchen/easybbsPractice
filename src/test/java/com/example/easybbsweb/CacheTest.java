package com.example.easybbsweb;

import com.example.easybbsweb.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CacheTest {
    @Test
    void removeCache1(){
        RedisUtils.del("11235:lawAidInfo");
    }
}
