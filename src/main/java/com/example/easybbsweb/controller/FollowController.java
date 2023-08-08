package com.example.easybbsweb.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.service.FollowService;
import com.example.easybbsweb.service.UniversityService;
import com.example.easybbsweb.utils.TokenUtil;
import com.qiniu.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.el.parser.Token;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/follow")
@Tag(name = "关注者与被关注者接口")
public class FollowController {
    @Resource
    FollowService followService;

    @Operation(summary = "关注")
    @PostMapping("/follow")
    public ResultInfo follow(
            @Parameter(description = "关注人的id") @RequestParam("id") Long uid,
            @RequestHeader("token") String token
    ) {
        followService.follow(TokenUtil.getCurrentUserId(token), uid);
        return ResultInfo.Success();
    }

    @Operation(summary = "取消关注")
    @PostMapping("/unfollow")
    public ResultInfo unfollow(
             @Parameter(description = "取消关注人的id") @RequestParam("id") Long uid,
            @RequestHeader("token") String token
    ) {
        followService.unfollow(TokenUtil.getCurrentUserId(token), uid);
        return ResultInfo.Success();
    }
    @Operation(summary = "是否关注了")
    @GetMapping("/check/follow")
    public ResultInfo checkFollow(
            @Parameter(description = "id") @RequestParam("id") Long uid,
            @RequestHeader("token") String token
    ) {

        return ResultInfo.Success(followService.checkFollow(TokenUtil.getCurrentUserId(token),uid));
    }

    @Operation(summary = "获取关注的人")
    @GetMapping("/get/followees")
    @ApiResponse(
            responseCode = "200", description = "success",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Long.class)))
    )
    public ResultInfo getFollowee(
            @RequestHeader("token") String token
    ) {
        List<Long> followee = followService.getFollowee(TokenUtil.getCurrentUserId(token));
        return ResultInfo.Success(JSON.toJSON(followee));
    }

    @Operation(summary = "获取粉丝")
    @GetMapping("/get/followers")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Long.class)))
    )
    public ResultInfo getFollower(
            @RequestHeader("token") String token
    ) {
        List<Long> follower = followService.getFollower(TokenUtil.getCurrentUserId(token));
        return ResultInfo.Success(JSON.toJSON(follower));
    }
}
