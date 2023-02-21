package com.example.easybbsweb.controller;

import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.entity.Comment;
import com.example.easybbsweb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.PortUnreachableException;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/loadComment")
    public ResultInfo loadComment(@RequestBody Article article){
        if(article.getArticleId()==null||article.getArticleId().equals("")){
            return new ResultInfo(false,"文章id不能为空",null);
        }
        //在这里如果前端没有给出页码则设置页码为1
        article.setPageNo(article.getPageNo()==null?1:article.getPageNo());
        List<Comment> articleCommentByArticleId = commentService.getArticleCommentByArticleId(article);
        return new ResultInfo(true,"响应成功",articleCommentByArticleId);
    }
}
