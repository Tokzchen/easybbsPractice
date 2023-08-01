package com.example.easybbsweb.controller;


import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/geo")
public class LocationController {

    @PostMapping("/save")
    @Operation(summary = "登记用户经纬度信息")
    public ResultInfo save(@RequestBody Map map,@RequestHeader("token") String token){
        String longtitude = (String) map.get("longtitude");
        String latitude = (String) map.get("latitude");
        String currentUserOrUniId = TokenUtil.getCurrentUserOrUniId(token);
        Point point = new Point(Double.parseDouble(longtitude), Double.parseDouble(latitude));
        return null;
        //save(point,userOrUniId)
    }
}
