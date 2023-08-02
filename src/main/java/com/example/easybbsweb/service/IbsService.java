package com.example.easybbsweb.service;

import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;

public interface IbsService {

    void saveUserLocation(Point point,String userId);

    void saveUniversityLocation(Point point,String uniId);


    GeoResults<RedisGeoCommands.GeoLocation<Object>> getNearbyUserInfo(Point point, Integer kilometer, Integer limit);

    GeoResults<RedisGeoCommands.GeoLocation<Object>> getNearbyUniversityInfo(Point point,Integer kilometer,Integer limit);




}
