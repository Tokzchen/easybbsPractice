package com.example.easybbsweb.controller;

import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.entity.Comment;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.service.CommentService;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.PortUnreachableException;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    AccountService accountService;

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


    @PostMapping("/postComment")
    public ResultInfo postComment(@RequestBody Comment comment, @RequestHeader("token") String token){
       if( comment.getArticleId().equals("")||comment.getArticleId()==null){
           return new ResultInfo(false,"评论缺少文章id",null);
       }
       if(comment.getpCommentId()==null||comment.getpCommentId().equals("")){
           return new ResultInfo(false,"评论缺少父级评论信息，如果是一级评论请传0",null);
       }
       if(comment.getContent()==null||comment.getContent().equals("")){
           return  new ResultInfo(false,"评论内容不得为空",null);
       }
        String currentUserId = TokenUtil.getCurrentUserId(token);
        UserInfo userNickNameByUserId = accountService.getUserInfoByUserId(currentUserId);
        boolean b = commentService.postComment(comment,userNickNameByUserId);
       if(b){
           return new ResultInfo(true,"评论成功",null);
       }else{
           return new ResultInfo(false,"评论失败，文章或许不存在",null);
       }

    }

}
