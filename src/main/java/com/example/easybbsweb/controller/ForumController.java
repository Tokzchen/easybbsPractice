package com.example.easybbsweb.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybbsweb.controller.request.forum.ForumArticleReq;
import com.example.easybbsweb.controller.response.forum.ForumArticleResp;
import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.service.impl.ForumArticleService;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static cn.hutool.core.lang.Console.log;

@Slf4j
@RestController
@RequestMapping(value = "/forum/article")
@Tag(name = "讨论区接口")
public class ForumController {
    @Resource
    ForumArticleService forumArticleService;

    @Operation(summary = "测试接口", description = "接口描述")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/zip", schema = @Schema(type = "string", format = "binary"))
    )

    @GetMapping("/t")
    public String test(@Parameter(description = "接口参数", required = false, example = "我是参数例子") String s) {

        return "ok";
    }

    @Operation(summary = "保存发布的文章")
    @PostMapping("/save")
    public ResultInfo saveArticle(
            @Parameter(description = "文章标题和内容", example = "{'title':' xxx','content':'xxx'")
            @RequestBody @Valid ForumArticleReq dto, HttpServletRequest request) {
        String token = request.getHeader("token");
        String email = TokenUtil.getCurrentEmail(token);
        forumArticleService.saveArticle(dto, email);
        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "删除文章")
    @PostMapping("/delete")
    public ResultInfo deleteArticleById(
            @Parameter(description = "文章的ID") @RequestParam("id") String id,
            @RequestHeader("token") String token) {
        forumArticleService.deleteArticle(id,TokenUtil.getCurrentEmail(token));
        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "更新文章")
    @PostMapping("/update")
    public ResultInfo updateArticleById(
            @Parameter(description = "代替原来文章新的文章对象") @RequestBody ForumArticleReq dto) {
        forumArticleService.updateArticle(dto);
        return  ResultInfo.Success();
    }



    @Operation(summary = "获取一批XX文章")
    @GetMapping("/get/pages")
    public ResultInfo getArticles(
            @RequestParam(value = "key", required = false) @Parameter(description = "关键字查询,没有就是全查") String key,
            @RequestParam(value = "flag", required = false) @Parameter(description = "按照什么字段排序") Integer flag,
            @RequestParam(value = "id", required = false) @Parameter(description = "最后一条数据的id") String id,
            @RequestParam(value = "otherValue", required = false) @Parameter(description = "最后一条数据的其他排序属性值") String otherValue
    ) {
        ForumArticleResp resp = forumArticleService.getPages(id, otherValue, flag, key);
        return ResultInfo.Success(resp);
    }
    @Operation(summary = "获取一篇文章")
    @GetMapping("/get")
    public ResultInfo searchArticle(@RequestParam(value = "id" ) @Parameter(description = "文章id") String id){
        System.out.println(forumArticleService.findArticleById(id));
        return ResultInfo.Success(JSON.toJSON(forumArticleService.findArticleById(id)));
    }
    @Operation(summary = "喜欢文章")
    @PostMapping("/like")
    public ResultInfo likeComment(
            @Parameter(description = "文章") @RequestBody ForumArticleReq dto,
            @RequestHeader("token") String token
    ) {
        forumArticleService.likeArticle(dto,TokenUtil.getCurrentEmail(token));
        return  ResultInfo.Success();
    }

    @Operation(summary = "不喜欢文章")
    @PostMapping("/unlike")
    public ResultInfo unlikeComment(
            @Parameter(description = "文章") @RequestBody ForumArticleReq dto,
            @RequestHeader("token") String token
    ) {
        forumArticleService.likeArticle(dto,TokenUtil.getCurrentEmail(token));
        return  ResultInfo.Success();
    }


}
