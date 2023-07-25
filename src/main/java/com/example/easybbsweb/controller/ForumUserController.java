package com.example.easybbsweb.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.service.impl.ForumUserService;
import com.example.easybbsweb.utils.TokenUtil;
import com.qiniu.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.xwpf.usermodel.TOC;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum/user")
@Tag(name = "关注者与被关注者接口")
public class ForumUserController {
    @Resource
    ForumUserService forumUserService;

    @Operation(summary = "关注")
    @PostMapping("/follow")
    public ResultInfo follow(@Parameter(description = "关注者Email") @RequestParam("email") String email, HttpServletRequest request) {
        forumUserService.follow(TokenUtil.getCurrentUserEmailByRequest(request), email);
        return ResultInfo.OK();
    }

    @Operation(summary = "取关")
    @PostMapping("/unfollow")
    public ResultInfo unfollow(@Parameter(description = "关注者Email") @RequestParam("email") String email, HttpServletRequest request) {
        forumUserService.unfollow(TokenUtil.getCurrentUserEmailByRequest(request), email);
        return ResultInfo.OK();
    }

    @Operation(summary = "查看是否关注了")
    @GetMapping("/is/follow")
    public ResultInfo isFollow(@Parameter(description = "关注者Email") @RequestParam("email") String email, HttpServletRequest request) {
        boolean isFollow = forumUserService.isFollow(TokenUtil.getCurrentUserEmailByRequest(request), email);
        return ResultInfo.OK(isFollow);
    }

    @Operation(summary = "获取所有关注的人")
    @GetMapping("/get/following")
    public ResultInfo getFollowing(HttpServletRequest request) {
        List<String> following = forumUserService.getFollowing(TokenUtil.getCurrentUserEmailByRequest(request));
        Object json = JSON.toJSON(following);
        return ResultInfo.OK(json);
    }

    @Operation(summary = "获取所有粉丝")
    @GetMapping("/get/followers")
    public ResultInfo getFollowers(HttpServletRequest request) {
        List<String> followers = forumUserService.getFollowers(TokenUtil.getCurrentUserEmailByRequest(request));
        Object json = JSON.toJSON(followers);
        return ResultInfo.OK(json);
    }

    @Operation(summary = "测试接口：添加用户")
    @PostMapping("/add")
    public ResultInfo addForumUser(@Parameter(description = "用户Email") @RequestParam("email") String email) {
        forumUserService.saveForumUser(email);
        return ResultInfo.OK();
    }

}
