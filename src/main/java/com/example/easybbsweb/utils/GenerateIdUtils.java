package com.example.easybbsweb.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.example.easybbsweb.domain.IdSelector;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

public class GenerateIdUtils {
    private static final int workId=1;
    private static final int dateCenterId=1;



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

    public static Long generateIdByEntity(String idSelector){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("10");//2
        stringBuilder.append(System.currentTimeMillis());//13
        stringBuilder.append((int)Math.random()*10).append((int)Math.random()*10);//2
        stringBuilder.append(idSelector);//1-2位
        Long bigInteger = new Long(stringBuilder.toString());
        return bigInteger;
    }


}
