package com.example.easybbsweb;

import com.example.easybbsweb.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.List;

@SpringBootTest
public class RedisLocationTest {
    @Test
    void func1(){
        //redisLocation data add
        RedisUtils.addLocation("userId1",
                new Point(120.197246,30.193334)
                ,"test_user_geo");
        RedisUtils.addLocation("userId2",
                new Point(120.197246,30.193331)
                ,"test_user_geo");

        RedisUtils.addLocation("userId3",
                new Point(120.197246,30.193434)
                ,"test_user_geo");

        RedisUtils.addLocation("userId4",
                new Point(120.197246,30.193338)
                ,"test_user_geo");


        GeoResults<RedisGeoCommands.GeoLocation<Object>> test_user_geo = RedisUtils.nearLocation(new Point(120.197246, 30.193329), 80 * 1000, 2, "test_user_geo");
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = test_user_geo.getContent();
        System.out.println(content);
        System.out.println(test_user_geo);
    }

    @Test
    void func2(){
//        RedisUtils.addLocation("userId4",
//                new Point(113.388300,23.06090)
//                ,RedisUtils.GEO_KEY_USER);
//        RedisUtils.addLocation("userId4",
//                new Point(113.388310,23.06093)
//                ,RedisUtils.GEO_KEY_USER);
//        GeoResults<RedisGeoCommands.GeoLocation<Object>> test_user_geo = RedisUtils.nearLocation(new Point(113.388302, 23.06095), 80 * 1000, 2, RedisUtils.GEO_KEY_USER);
//        System.out.println(test_user_geo);
    }

    @Test
    void func3(){;
        RedisUtils.del("11235:lawAid:apply:cnt");
    }
}
