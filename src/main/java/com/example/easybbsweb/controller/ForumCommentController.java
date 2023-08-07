package com.example.easybbsweb.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.controller.response.forum.CommentResp;
import com.example.easybbsweb.domain.entity.ForumArticleComment;
import com.example.easybbsweb.service.ForumArticleCommentLikesService;
import com.example.easybbsweb.service.ForumArticleCommentService;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum/comment")
@Tag(name = "一级评论接口")
public class ForumCommentController {
    @Resource
    ForumArticleCommentService commentService;
    @Resource
    ForumArticleCommentLikesService commentLikesService;

    @Operation(summary = "删除评论")
    @PostMapping("/delete")
    public ResultInfo deleteComment(
            @Parameter(description = "要删除的评论id") @RequestParam("id") Long id) {
        commentService.deleteById(id);
        return ResultInfo.Success();
    }

    @Operation(summary = "发布评论,并返回数据库中的id")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = CommentResp.class)))
    @PostMapping("/publish")
    public ResultInfo publishComment(
            @Parameter(description = "评论") @RequestBody CommentResp comment,
            @RequestHeader("token") String token
            ) {
        CommentResp publish = commentService.publish(TokenUtil.getCurrentUserId(token), comment);
        return ResultInfo.Success(publish);
    }
    @Operation(summary = "根据文章id获取所有评论")
    @GetMapping("/get")
    public ResultInfo publishComment(
            @Parameter(description = "文章id") @RequestParam("id") Long article_id,
            @RequestHeader("token") String token
    ) {
        List<ForumArticleComment> list =  commentService.getByArticleId(article_id);
        return ResultInfo.Success(list);
    }

    @Operation(summary = "喜欢评论")
    @PostMapping("/like")
    public ResultInfo likeComment(
            @Parameter(description = "喜欢的评论id") @RequestParam("id") Long cmtId,
            @RequestHeader("token") String token
    ) {
        commentLikesService.like(TokenUtil.getCurrentUserId(token), cmtId);
        return ResultInfo.Success();
    }

    @Operation(summary = "不喜欢评论")
    @PostMapping("/unlike")
    public ResultInfo unlikeComment(
            @Parameter(description = "不喜欢的评论id") @RequestParam("id") Long cmtId,
            @RequestHeader("token") String token
    ) {
        commentLikesService.unLike(TokenUtil.getCurrentUserId(token), cmtId);
        return ResultInfo.Success();
    }
    @Operation(summary = "是否喜欢这个评论")
    @GetMapping("/check/like")
    public ResultInfo checkLikeComment(
            @Parameter(description = "评论id") @RequestParam("id") Long cmtId,
            @RequestHeader("token") String token
    ) {

        return ResultInfo.Success(commentLikesService.checkLike(TokenUtil.getCurrentUserId(token), cmtId));
    }


}
