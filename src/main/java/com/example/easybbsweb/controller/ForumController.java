package com.example.easybbsweb.controller;

import com.example.easybbsweb.controller.request.ForumArticleReq;
import com.example.easybbsweb.controller.response.ForumArticleResp;
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
            @RequestBody @Valid ForumArticleReq dto, HttpServletRequest request) {
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
            @Parameter(description = "代替原来文章新的文章对象") @Valid ForumArticleReq dto, HttpServletRequest request) {
        String token = request.getHeader("token");
        String email = TokenUtil.getCurrentEmail(token);
        forumArticleService.saveArticle(dto, email);
        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "获取一批XX文章")
    @GetMapping("/get/pages")
    public ResultInfo getArticles(
            @RequestParam(value = "key",required = false) @Parameter(description = "关键字查询,没有就是全查") String key,
            @RequestParam(value = "flag",required = false) @Parameter(description = "按照什么字段排序") Integer flag,
            @RequestParam(value = "id",required = false) @Parameter(description = "最后一条数据的id") String id,
            @RequestParam(value = "otherValue",required = false) @Parameter(description = "最后一条数据的其他排序属性值") Object otherValue
    ) {
        List<ForumArticleResp> data = forumArticleService.getPages(id, otherValue, flag, key);
        return ResultInfo.OK(data);
    }


}
