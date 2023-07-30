package com.example.easybbsweb.controller;

import com.example.easybbsweb.controller.request.forum.ForumArticleReq;
import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.service.impl.ForumSecondCommentService;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forum/second/comment")
public class ForumSecondCommentController {
    @Resource
    ForumSecondCommentService secondCommentService;
    @Operation(summary = "删除二级评论")
    @PostMapping("/delete")
    public ResultInfo deleteSecondComment(
            @Parameter(description = "代替原来文章新的文章对象") @RequestBody ForumArticleReq dto,
            @Parameter(description = "要删除的评论id") @RequestParam("id")String id) {
        secondCommentService.deleteSecondComment(dto, id);
        return  ResultInfo.Success();
    }
    @Operation(summary = "发布二级评论")
    @PostMapping("/publish")
    public ResultInfo publishSecondComment(
            @Parameter(description = "代替原来文章新的文章对象") @RequestBody ForumArticleReq dto) {
        secondCommentService.updateSecondComment(dto);
        return  ResultInfo.Success();
    }
    @Operation(summary = "喜欢二级评论")
    @PostMapping("/like")
    public ResultInfo likeSecondComment(
            @Parameter(description = "代替原来文章新的文章对象") @RequestBody ForumArticleReq dto,
            @Parameter(description = "喜欢的评论id") @RequestParam("id")String id,
            @RequestHeader("token") String token
    ) {
        secondCommentService.likeSecondComment(dto,  TokenUtil.getCurrentEmail(token),id);
        return  ResultInfo.Success();
    }
    @Operation(summary = "不喜欢二级评论")
    @PostMapping("/unlike")
    public ResultInfo unlikeSecondComment(
            @Parameter(description = "代替原来文章新的文章对象") @RequestBody ForumArticleReq dto,
            @Parameter(description = "不喜欢的评论id") @RequestParam("id")String id,
            @RequestHeader("token") String token
    ) {
        secondCommentService.unlikeSecondComment(dto,  TokenUtil.getCurrentEmail(token),id);
        return  ResultInfo.Success();
    }

}
