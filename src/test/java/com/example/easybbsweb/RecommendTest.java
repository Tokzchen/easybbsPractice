package com.example.easybbsweb;


import com.example.easybbsweb.domain.others.lawAid.UniversityPair;
import com.example.easybbsweb.service.LawAidService;
import com.example.easybbsweb.service.impl.LawAidServiceImpl;
import com.example.easybbsweb.utils.RedisUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Vector;

@Slf4j
@SpringBootTest
public class RecommendTest {
    @Resource
    LawAidServiceImpl lawAidService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    void func1(){
        //测试
        Point point=new Point(113.23,23.16);
        String area="民间借贷纠纷";
        Long userId= Long.valueOf(11235);
        List<UniversityPair> universityPairs = lawAidService.generateAndRecommendUniversities(userId, point, area);
        log.info("三个指标完全添加后的{}",universityPairs);

    }

    @Test
    void func2(){
        Vector<UniversityPair> universityPairs = new Vector<>();
        Point point=new Point(113.23,23.16);
        String area="民间借贷纠纷";
        Long userId= Long.valueOf(11235);
        lawAidService.generateIndexOneForUser(userId,point,universityPairs);
        log.info("指数产生后的vector:{}",universityPairs);

    }

    @Test
    void func3(){
        RedisUtils.addLocation("10014",new Point(113.232,23.16),RedisUtils.GEO_KEY_UNIVERSITY  );
        Point point=new Point(113.23,23.16);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = RedisUtils.nearLocation(point, 200 * 1000, 20, RedisUtils.GEO_KEY_UNIVERSITY);
        log.info("附近的大学是{}",geoResults);
    }

    @Test
    void func4(){
        Vector<UniversityPair> list = new Vector<>();
        lawAidService.generateIndexTwoForUser(list);
        log.info("添加Index2后的list:{}",list);
    }

    @Test
    void func5(){
        String area="民间借贷纠纷";
        Vector<UniversityPair> universityPairs = new Vector<>();
        lawAidService.generateIndexThreeForUser(universityPairs,area);
        log.info("添加index3后的list:{}",universityPairs);
    }
    @Test
    void testRedisCacheSucessOrNot(){
        Object c1 = RedisUtils.get("lawAid:index2");
        Object c2 = RedisUtils.get("lawAid:index2:lastUpdateTime");
        Object c3 = RedisUtils.get("lawAid:index3:民间借贷纠纷");
        log.info("缓存1{}，缓存2{}，缓存3{}",c1,c2,c3);
    }

















}
