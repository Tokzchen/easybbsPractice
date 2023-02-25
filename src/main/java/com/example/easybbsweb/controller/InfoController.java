package com.example.easybbsweb.controller;

import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.LikeRecord;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.service.LikeService;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/info")
public class InfoController {
    @Autowired
    AccountService accountService;
    @Autowired
    LikeService likeService;


    @GetMapping("/userInfo")
    public ResultInfo getUserInfo(@RequestHeader("token") String token, HttpServletRequest req){
        String remoteAddr = req.getRemoteAddr();
        log.info("登录用户的ip地址是{}",remoteAddr);
        //因为这里的ip地址在测试环境中是本机，在生产环境中，以下代码还需要进行
        //1.修改数据库用户ip,
        //2.将ip数据返回给前端
        //3.获取用户点赞记录
        String currentEmail= TokenUtil.getCurrentUserName(token);
        UserInfo userInfoByEmail = accountService.getUserInfoByEmail(currentEmail);
        List<LikeRecord> likeRecordsByUserId = likeService.getLikeRecordsByUserId(userInfoByEmail.getUserId());
        userInfoByEmail.setLikeRecords(likeRecordsByUserId);
        return new ResultInfo(true,"响应成功",userInfoByEmail);
    }


    @PostMapping("/likeList")
    public ResultInfo getUserLikeList(@RequestHeader("token") String token){
        String currentUserId = TokenUtil.getCurrentUserId(token);
        List<LikeRecord> likeRecordsByUserId = likeService.getLikeRecordsByUserId(currentUserId);
        return new ResultInfo(true,"响应成功",likeRecordsByUserId);
    }

    @PostMapping("/userNickName")
    public ResultInfo getUserNickName(@RequestBody UserInfo userInfo){
        if(userInfo.getUserId()==null||userInfo.getUserId().equals("")){
            return new ResultInfo(false,"用户不存在",null);
        }else{
            UserInfo userNickNameByUserId = accountService.getUserNickNameByUserId(userInfo.getUserId());
            return new ResultInfo(true,"响应成功",userInfo.getNickName());
        }
    }

}
