package com.example.easybbsweb.utils;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Component
public class RedisGenerateIdUtils {
    public static final String USER="user";
    public static final String UNIVERSITY="university";
    public static final String LAW_AID="lawAid";
    public static long BEGIN_TIMESTAMP=1672531200;//2023.1.1 0.0.0
    public static int CNT_BIT=32;

    public long nextId(String prefix){
        //计算timestamp
        long nowStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long timestamp=nowStamp-BEGIN_TIMESTAMP;
        //计算序列号
        String dateKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        long count = RedisUtils.incr("icr:" + prefix + ":" + dateKey, 1);
        return nowStamp<<CNT_BIT|count;
    }


}
