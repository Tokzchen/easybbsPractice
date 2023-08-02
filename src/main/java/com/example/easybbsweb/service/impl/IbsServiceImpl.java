package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.service.IbsService;
import com.example.easybbsweb.utils.RedisUtils;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class IbsServiceImpl implements IbsService {
    @Override
    public void saveUserLocation(Point point, String userId) {
        if(!StringUtils.hasText(userId)){
            throw new BusinessException("位置信息归属不明确");
        }
        RedisUtils.addLocation(userId,point,RedisUtils.GEO_KEY_USER);
    }

    @Override
    public void saveUniversityLocation(Point point, String uniId) {
        if(!StringUtils.hasText(uniId)){
            throw new BusinessException("位置信息归属不明确");
        }
        RedisUtils.addLocation(uniId,point,RedisUtils.GEO_KEY_USER);

    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> getNearbyUserInfo(Point point, Integer kilometer, Integer limit) {
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = RedisUtils.nearLocation(point, kilometer * 1000, limit, RedisUtils.GEO_KEY_USER);
        return geoResults;
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> getNearbyUniversityInfo(Point point, Integer kilometer, Integer limit) {
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = RedisUtils.nearLocation(point, kilometer * 1000, limit, RedisUtils.GEO_KEY_UNIVERSITY);
        return geoResults;
    }


}
