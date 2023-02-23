package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.entity.Comment;
import com.example.easybbsweb.domain.entity.UserInfo;

import java.util.List;

public interface CommentService {
    List<Comment> getArticleCommentByArticleId(Article article);

    boolean postComment(Comment comment, UserInfo userInfo);
}
