package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getArticleCommentByArticleId(Article article);
}
