package com.example.easybbsweb.controller;

import com.example.easybbsweb.controller.request.forum.ForumArticleReq;
import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.service.impl.ForumCommentService;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forum/comment")
public class ForumCommentController {
    @Resource
    ForumCommentService commentService;
    @Operation(summary = "删除评论")
    @PostMapping("/delete")
    public ResultInfo deleteComment(
            @Parameter(description = "包含评论文章") @RequestBody ForumArticleReq dto,
            @Parameter(description = "要删除的评论id") @RequestParam("id")String id) {
        commentService.deleteComment(dto, id);
        return  ResultInfo.Success();
    }
    @Operation(summary = "发布评论")
    @PostMapping("/publish")
    public ResultInfo publishComment(
            @Parameter(description = "包含评论文章") @RequestBody ForumArticleReq dto) {
        commentService.updateComment(dto);
        return  ResultInfo.Success();
    }
    @Operation(summary = "喜欢评论")
    @PostMapping("/like")
    public ResultInfo likeComment(
            @Parameter(description = "包含评论文章") @RequestBody ForumArticleReq dto,
            @Parameter(description = "喜欢的评论id") @RequestParam("id")String id,
            @RequestHeader("token") String token
    ) {
        commentService.likeComment(dto, TokenUtil.getCurrentEmail(token),id);
        return  ResultInfo.Success();
    }
    @Operation(summary = "不喜欢评论")
    @PostMapping("/unlike")
    public ResultInfo unlikeComment(
            @Parameter(description = "包含评论文章") @RequestBody ForumArticleReq dto,
            @Parameter(description = "不喜欢的评论id") @RequestParam("id")String id,
            @RequestHeader("token") String token
    ) {
        commentService.unlikeComment(dto,  TokenUtil.getCurrentEmail(token), id);
        return  ResultInfo.Success();
    }


}
