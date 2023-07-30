package com.example.easybbsweb.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.service.impl.ForumUserService;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum/user")
@Tag(name = "关注者与被关注者接口")
public class ForumUserController {
    @Resource
    ForumUserService forumUserService;
    @Resource
    AccountService accountService;

    @Operation(summary = "关注")
    @PostMapping("/follow")
    public ResultInfo follow(@Parameter(description = "关注者Email") @RequestParam("email") String BeFollowedEmail, 
                             @RequestHeader("token") String token) {
        forumUserService.follow(TokenUtil.getCurrentEmail(token),BeFollowedEmail);
        return ResultInfo.Success();
    }

    @Operation(summary = "取关")
    @PostMapping("/unfollow")
    public ResultInfo unfollow(@Parameter(description = "关注者Email") @RequestParam("email") String BeFollowedEmail,
                               @RequestHeader("token") String token) {
        forumUserService.unfollow(TokenUtil.getCurrentEmail(token),BeFollowedEmail);
        return ResultInfo.Success();
    }

    @Operation(summary = "查看是否关注了")
    @GetMapping("/is/follow")
    public ResultInfo isFollow(@Parameter(description = "关注者Email") @RequestParam("email") String BeFollowedEmail,
                               @RequestHeader("token") String token) {
        boolean isFollow = forumUserService.isFollow(TokenUtil.getCurrentEmail(token),BeFollowedEmail);
        return ResultInfo.Success(isFollow);
    }

    @Operation(summary = "获取所有关注的人")
    @GetMapping("/get/following")
    public ResultInfo getFollowing(@RequestHeader("token") String token) {
        List<String> following = forumUserService.getFollowing(TokenUtil.getCurrentEmail(token));
        Object json = JSON.toJSON(following);
        return ResultInfo.Success(json);
    }

    @Operation(summary = "获取所有粉丝")
    @GetMapping("/get/followers")
    public ResultInfo getFollowers(@RequestHeader("token") String token) {
        List<String> followers = forumUserService.getFollowers(TokenUtil.getCurrentEmail(token));
        Object json = JSON.toJSON(followers);
        return ResultInfo.Success(json);
    }
    @Operation(summary = "帖子数")
    @GetMapping("/get/article/count")
    public ResultInfo getArticlesCount(@RequestHeader("token") String token) {
        Integer count = forumUserService.getArticlesCount(TokenUtil.getCurrentEmail(token));
        return ResultInfo.Success(count);
    }
    @Resource
    MongoProperties mongoProperties;
    @Operation(summary = "测试接口：添加用户")
    @PostMapping("/add")
    public ResultInfo addForumUser(@Parameter(description = "用户Email") @RequestParam("email") String email) {
        System.out.println(mongoProperties.getUsername());
        forumUserService.saveForumUser(email);
        return ResultInfo.Success();
    }

    @Operation(summary = "使用用户token同步mongodb")
    @GetMapping("/add/token")
    public ResultInfo addForumUserToken(@RequestHeader("token") String token){
        String currentEmail = TokenUtil.getCurrentEmail(token);
        forumUserService.saveForumUser(currentEmail);
        return ResultInfo.Success();
    }

}
