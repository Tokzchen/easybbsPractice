package com.example.easybbsweb.controller;

import com.example.easybbsweb.controller.response.forum.FirstForumArticleResp;
import com.example.easybbsweb.controller.response.forum.ForumArticleRespFromES;
import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.example.easybbsweb.service.ForumArticleLikesService;
import com.example.easybbsweb.service.ForumArticleService;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static cn.hutool.core.lang.Console.log;

@Slf4j
@RestController
@RequestMapping(value = "/forum/article")
@Tag(name = "文章接口")
public class ForumController {
    @Resource
    ForumArticleService forumArticleService;
    @Resource
    ForumArticleLikesService forumArticleLikesService;
    @Operation(summary = "点赞唯一标识")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = Void.class)))
    @PostMapping("/identifies")
    public ResultInfo identifies(
            @Parameter(description = "生成的唯一标识") @RequestParam("identifies") String identifies
    ){
        forumArticleLikesService.identifies(identifies);
        return ResultInfo.Success();
    }

    @Operation(summary = "保存发布的文章")
    @PostMapping("/save")
    public ResultInfo saveArticle(
            @Parameter(description = "文章标题和内容", example = "{'title':' xxx','content':'xxx'")
            @RequestBody @Valid ForumArticle forumArticle,
            @RequestHeader("token") String token
    ) {

        forumArticleService.publish(forumArticle, TokenUtil.getCurrentUserId(token));
        return new ResultInfo(true, "ok", null);
    }

    @Operation(summary = "删除文章")
    @PostMapping("/delete")
    public ResultInfo deleteArticleById(
            @Parameter(description = "文章的ID") @RequestParam("id") Long id) {
        forumArticleService.deleteById(id);
        return new ResultInfo(true, "ok", null);
    }
    @Operation(summary = "更新文章")
    @PostMapping("/update")
    public ResultInfo updateArticleById(
            @Parameter(description = "代替原来文章新的文章对象") @RequestBody ForumArticle forumArticle) {
        forumArticleService.update(forumArticle);
        return ResultInfo.Success();
    }


    @Operation(summary = "获取一批XX文章,只简单的返回文章部分数据")
    @GetMapping("/get/pages")
    public ResultInfo getArticles(
            @RequestParam(value = "key", required = false) @Parameter(description = "关键字查询,没有就是全查") String key,
            @RequestParam(value = "flag", required = false) @Parameter(description = "按照什么字段排序") Integer flag,
            @RequestParam(value = "id", required = false) @Parameter(description = "最后一条数据的id") Long id,
            @RequestParam(value = "otherValue", required = false) @Parameter(description = "最后一条数据的其他排序属性值") Object otherValue
    ) {
        ForumArticleRespFromES resp = forumArticleService.SearchArticles(id, otherValue, flag, key);

        return ResultInfo.Success(resp);
    }

    @Operation(summary = "获取一篇作者的文章")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ForumArticleRespFromES.class)))
    @GetMapping("/get/my/pages")
    public ResultInfo getMyArticles(
            @RequestParam(value = "key", required = false) @Parameter(description = "关键字查询,没有就是全查") String key,
            @RequestParam(value = "flag", required = false) @Parameter(description = "按照什么字段排序") Integer flag,
            @RequestParam(value = "id", required = false) @Parameter(description = "最后一条数据的id") Long id,
            @RequestParam(value = "otherValue", required = false) @Parameter(description = "最后一条数据的其他排序属性值") Object otherValue,
            @RequestHeader("token") String token
    ) {
        ForumArticleRespFromES resp = forumArticleService.searchMyArticles(id, otherValue, flag, key,TokenUtil.getCurrentUserId(token));

        return ResultInfo.Success(resp);
    }

    @Operation(summary = "根据id获取一篇文章，包含10条评论和所属的所有二级评论，加上对应作者")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = FirstForumArticleResp.class)))
    @GetMapping("/get/complex")
    public ResultInfo getArticleAndCommentAndUserInfoById(@RequestParam(value = "id") @Parameter(description = "文章id") Long id) {
        return ResultInfo.Success(forumArticleService.getArticleAndCommentAndUserInfoById(id));
    }
    @Operation(summary = "根据id获取一篇文章")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = FirstForumArticleResp.class)))
    @GetMapping("/get")
    public ResultInfo getArticlesById(@RequestParam(value = "id") @Parameter(description = "文章id") Long id) {
        return ResultInfo.Success(forumArticleService.getArticleById(id));
    }


    @Operation(summary = "喜欢文章")
    @PostMapping("/like")
    public ResultInfo like(
            @Parameter(description = "文章Id") @RequestParam("id") Long articleId,
            @RequestHeader("token") String token,
            @Parameter(description = "唯一标识") @RequestParam("identifies") String identifies
    ) {
        forumArticleLikesService.sendLike(TokenUtil.getCurrentUserId(token), articleId,true,identifies);
        return ResultInfo.Success();
    }

    @Operation(summary = "获取这个人发布文章的数量")
    @PostMapping("/get/count")
    public ResultInfo getCountByUid(
            @RequestHeader("token") String token
    ) {
        return ResultInfo.Success(forumArticleService.getArticlesCountByUid(TokenUtil.getCurrentUserId(token)));
    }

    @Operation(summary = "不喜欢")
    @PostMapping("/unlike")
    public ResultInfo unLike(
            @Parameter(description = "文章Id") @RequestParam("id") Long articleId,
            @RequestHeader("token") String token,
            @Parameter(description = "唯一标识") @RequestParam("identifies") String identifies
    ) {
        forumArticleLikesService.sendLike(TokenUtil.getCurrentUserId(token), articleId,false,identifies);
        return ResultInfo.Success();
    }

    @Operation(summary = "是否喜欢文章")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = boolean.class)))
    @GetMapping("/check/like")
    public ResultInfo checkLike(
            @Parameter(description = "文章id") @RequestParam("id") Long cmtId,
            @RequestHeader("token") String token
    ) {

        return ResultInfo.Success(forumArticleLikesService.checkLike(TokenUtil.getCurrentUserId(token), cmtId));
    }


}
