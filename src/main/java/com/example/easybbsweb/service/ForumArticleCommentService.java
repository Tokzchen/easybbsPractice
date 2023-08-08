package com.example.easybbsweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.easybbsweb.controller.response.forum.CommentResp;
import com.example.easybbsweb.domain.entity.ForumArticleComment;

import java.util.List;


public interface ForumArticleCommentService extends IService<ForumArticleComment> {
    public CommentResp publish(Long uid, CommentResp forumArticle);

    public void update(ForumArticleComment forumArticle);
    public void deleteById(Long id);


    List<ForumArticleComment> getPage(Long articleId, long from, long to);

    List<ForumArticleComment> getByArticleId(Long articleId);
}
