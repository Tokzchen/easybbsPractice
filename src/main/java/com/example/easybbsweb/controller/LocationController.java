package com.example.easybbsweb.controller;


import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.domain.others.Location;
import com.example.easybbsweb.service.IbsService;
import com.example.easybbsweb.utils.RedisUtils;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geo")
public class LocationController {
    @Resource
    IbsService ibsService;


    @PostMapping("/save")
    @Operation(summary = "登记用户经纬度信息")
    public ResultInfo save(@RequestBody Location location, @RequestHeader("token") String token){
        String userOrUniId = TokenUtil.getCurrentUserOrUniId(token);
        //long,lat
        Point point = new Point(location.getLng(), location.getLat());
        //根据用户identity进行不同逻辑处理
        String identity = (String) RedisUtils.get(token + ":identity");
        if(identity.equals("user")){
            ibsService.saveUserLocation(point,userOrUniId);
        }else if(identity.equals("university")){
            ibsService.saveUniversityLocation(point,userOrUniId);
        }else{
            return ResultInfo.Fail();
        }
        //缓存
        RedisUtils.set(userOrUniId+":location",location);
        return ResultInfo.OK();
    }

    @PostMapping("/near/user/{kilometer}")
    @Operation(summary = "获取一个坐标附近的用户")
    public ResultInfo getNearbyUsers(@PathVariable("kilometer") Integer kilometer, @io.swagger.v3.oas.annotations.parameters.RequestBody Location location){
        GeoResults<RedisGeoCommands.GeoLocation<Object>> nearbyUserInfo = ibsService.getNearbyUserInfo(new Point(location.getLng(), location.getLat()), kilometer, 20);
        return ResultInfo.Success(nearbyUserInfo);
    }

}
