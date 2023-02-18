package com.example.easybbsweb.controller;

import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/info")
public class InfoController {
    @Autowired
    AccountService accountService;
    @GetMapping("/userInfo")
    public ResultInfo getUserInfo(@RequestHeader("token") String token, HttpServletRequest req){
        String remoteAddr = req.getRemoteAddr();
        log.info("登录用户的ip地址是{}",remoteAddr);
        //因为这里的ip地址在测试环境中是本机，在生产环境中，以下代码还需要进行
        //1.修改数据库用户ip,
        //2.将ip数据返回给前端
        String currentEmail= TokenUtil.getCurrentUserName(token);
        UserInfo userInfoByEmail = accountService.getUserInfoByEmail(currentEmail);
        return new ResultInfo(true,"响应成功",userInfoByEmail);
    }

}
