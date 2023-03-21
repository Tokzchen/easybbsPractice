package com.example.easybbsweb.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.util.StringUtils;

public class GenerateIdUtils {

    public static final Snowflake snowflack= IdUtil.getSnowflake(1,1);
    public static String generateIdBySnowFlake(){

        return  snowflack.nextIdStr();
    }

    public static String generateID(){
        long millis = System.currentTimeMillis();
        String id=String.valueOf(millis).substring(0,10);
        return id;
    }
    public static Integer generateIDInteger(){
        long millis = System.currentTimeMillis();
        String id=String.valueOf(millis).substring(0,10);
        return Integer.parseInt(id);
    }


}
