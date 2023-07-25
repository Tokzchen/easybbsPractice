package com.example.easybbsweb.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybbsweb.controller.dto.ForumArticleDTO;
import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.repository.entity.ForumArticle;
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

import java.util.List;

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
    public ResultInfo SaveArticle(
            @Parameter(description = "文章标题和内容", example = "{'title':' xxx','content':'xxx'")
            @RequestBody @Valid ForumArticleDTO dto, HttpServletRequest request) {
        String token = request.getHeader("token");
        String email = TokenUtil.getCurrentEmail(token);
        forumArticleService.saveArticle(dto, email);
        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "删除文章")
    @PostMapping("/delete")
    public ResultInfo DeleteArticleById(
            @Parameter(description = "文章的ID")
            @RequestBody @Valid String id) {

        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "更新文章")
    @PostMapping("/update")
    public ResultInfo UpdateArticleById(
            @Parameter(description = "代替原来文章新的文章对象") @Valid ForumArticleDTO dto, HttpServletRequest request) {
        String token = request.getHeader("token");
        String email = TokenUtil.getCurrentEmail(token);
        forumArticleService.saveArticle(dto, email);
        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "获取一批老文章")
    @GetMapping("/get/pages")
    public ResultInfo getArticles(@RequestParam("page") @Parameter(description = "页数") Integer page) {
        List<ForumArticle> pages = forumArticleService.getPage(page);
        Object data = JSON.toJSON(pages);
        return new ResultInfo(true, "ok", data);
    }

    @Operation(summary = "获取一批新的文章")
    @GetMapping("/get/new/pages")
    public ResultInfo getNewArticles(@RequestParam("page") @Parameter(description = "页数") Integer page) {
        List<ForumArticle> pages = forumArticleService.getNewPage(page);
        Object data = JSON.toJSON(pages);
        return new ResultInfo(true, "ok", data);
    }

}
