package com.example.easybbsweb.controller;

import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Autowired
    AccountService accountService;
    @GetMapping("/userInfo")
    public ResultInfo getUserInfo(@RequestHeader("token") String token){
        String currentEmail= TokenUtil.getCurrentUserName(token);
        UserInfo userInfoByEmail = accountService.getUserInfoByEmail(currentEmail);
        return new ResultInfo(true,"响应成功",userInfoByEmail);
    }

}
