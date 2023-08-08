package com.example.easybbsweb.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.controller.response.forum.SecondCommentResp;
import com.example.easybbsweb.domain.entity.ForumArticleSecondComment;
import com.example.easybbsweb.service.ForumArticleSecondCommentLikesService;
import com.example.easybbsweb.service.ForumArticleSecondCommentService;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum/second/comment")
@Tag(name = "二级评论接口")
public class ForumSecondCommentController {
    @Resource
    ForumArticleSecondCommentService secondCommentService;
    @Resource
    ForumArticleSecondCommentLikesService secondCommentLikesService;
    @Operation(summary = "删除二级评论")
    @PostMapping("/delete")
    public ResultInfo deleteSecondComment(
            @Parameter(description = "要删除的评论id") @RequestParam("id")Long scmtId,
            @RequestHeader("token") String token
    ) {
        secondCommentService.deleteById(scmtId);
        return  ResultInfo.Success();
    }
    @Operation(summary = "发布二级评论")
    @PostMapping("/publish")
    public ResultInfo publishSecondComment(
            @Parameter(description = "代替原来文章新的文章对象") @RequestBody SecondCommentResp secondComment,
            @RequestHeader("token") String token
    ) {
        SecondCommentResp publish = secondCommentService.publish(TokenUtil.getCurrentUserId(token), secondComment);
        return  ResultInfo.Success(publish);
    }
    @Operation(summary = "喜欢二级评论")
    @PostMapping("/like")
    public ResultInfo likeSecondComment(
            @Parameter(description = "喜欢的评论id") @RequestParam("id")Long scmtId,
            @RequestHeader("token") String token
    ) {
        secondCommentLikesService.Like( TokenUtil.getCurrentUserId(token),scmtId);
        return  ResultInfo.Success();
    }
    @Operation(summary = "不喜欢二级评论")
    @PostMapping("/unlike")
    public ResultInfo unlikeSecondComment(
            @Parameter(description = "喜欢的评论id") @RequestParam("id")Long scmtId,
            @RequestHeader("token") String token
    ) {
        secondCommentLikesService.unLike( TokenUtil.getCurrentUserId(token),scmtId);
        return  ResultInfo.Success();
    }
    @Operation(summary = "根据评论id获取所有二级评论")
    @GetMapping("/get")
    public ResultInfo publishComment(
            @Parameter(description = "文章id") @RequestParam("id") Long cmtId,
            @RequestHeader("token") String token
    ) {
        List<ForumArticleSecondComment> list =  secondCommentService.getByCommentId(cmtId);
        return ResultInfo.Success((list));
    }
    @Operation(summary = "是否喜欢这个二级评论")
    @GetMapping("/check/like")
    public ResultInfo checkLike(
            @Parameter(description = "二级评论id") @RequestParam("id") Long cmtId,
            @RequestHeader("token") String token
    ) {

        return ResultInfo.Success(secondCommentLikesService.checkLike(TokenUtil.getCurrentUserId(token), cmtId));
    }

}
