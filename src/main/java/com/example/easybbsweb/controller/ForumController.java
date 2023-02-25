package com.example.easybbsweb.controller;


import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.others.PageInfo;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.service.ForumArticalService;
import com.example.easybbsweb.utils.ResultUtil;
import com.example.easybbsweb.utils.TokenUtil;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/forum")
public class ForumController {
    @Autowired
    ForumArticalService forumArticalService;

    @PostMapping("/loadArticle")
    public ResultInfo loadArtical(@RequestBody Article article){
        System.out.println(article);
        if(article.getpBoardId()==null||article.getpBoardId().equals("")||article.getpBoardId().equals(0)
                &&article.getpBoardName()==null||article.getpBoardName().equals("")||article.getpBoardName().equals("null")){
            //加载所有文章也就是首页
            PageInfo pageInfo =
                    forumArticalService.selectArticalAll(
                            article.getPageNo() == null ? 1 : article.getPageNo());
            return new ResultInfo(true,"响应成功",pageInfo);
        }else{
            //这里是加载其他板块的文章
            PageInfo pageInfo = forumArticalService.selectArticalBoard(article.getPageNo() == null ? 1 : article.getPageNo(), article.getpBoardName());
            return new ResultInfo(true,"响应成功",pageInfo);
        }
    }

    @PostMapping("/getArticleDetail")
    public ResultInfo getArticleInfo(@RequestBody Map map){
        String articleId= (String) map.get("articleId");
        if(articleId==null||articleId.equals("")){
            return new ResultInfo(false,"文章的id不得为空!",null);
        }else{
            Article articleDetail = forumArticalService.getArticleDetail(articleId);
            return new ResultInfo(true,"响应成功",articleDetail);
        }
    }

    @PostMapping("/doLike")
    public ResultInfo articleDoLike(@RequestBody Article article,@RequestHeader("token") String token){
        if(article.getArticleId()==null||article.getArticleId().equals("")){
            throw new BusinessException("点赞的文章不得为空!");
        }
        String userIdClickLike = TokenUtil.getCurrentUserId(token);
        Article article1 = new Article();
        article1.setArticleId(article.getArticleId());
        article1.setUserIdClickLike(userIdClickLike);
        boolean b = forumArticalService.articleDoLike(article1);
        if(b){
            return new ResultInfo(true,"点赞成功",null);
        }else{
            return new ResultInfo(false,"点赞失败",null);
        }
    }

}
