package com.example.easybbsweb.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybbsweb.controller.dto.ForumArticleDTO;
import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.repository.ForumArticle;
import com.example.easybbsweb.service.impl.ForumArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
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
            @Parameter(description = "文档的html", example = "{'html':'<p>title</p>','author':'name'}")
            @RequestBody @Valid ForumArticleDTO dto) {
        forumArticleService.save(forumArticleService.html2article(dto));
        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "保存发布的文章")
    @PostMapping("/delete")
    public ResultInfo DeleteArticleById(
            @Parameter(description = "文章的ID")
            @RequestBody @Valid String id) {

        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "更新文章")
    @PostMapping("/update")
    public ResultInfo UpdateArticleById(
            @Parameter(description = "代替原来文章新的文章对象") @Valid ForumArticleDTO dto) {
        forumArticleService.save(forumArticleService.html2article(dto));
        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "获取一批文章")
    @GetMapping("/get")
    public ResultInfo getArticles() {
        List<ForumArticle> pages = forumArticleService.getPage();
        return new ResultInfo(true, "ok", JSON.toJSON(pages));
    }

}
