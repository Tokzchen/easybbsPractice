package com.example.easybbsweb.service;

import com.example.easybbsweb.controller.response.forum.SecondCommentResp;
import com.example.easybbsweb.domain.entity.ForumArticleSecondComment;

import java.util.List;


public interface ForumArticleSecondCommentService {

    public SecondCommentResp publish(Long uid, SecondCommentResp secondComment);

    public void update(ForumArticleSecondComment secondComment);
    public void deleteById(Long id);


    List<ForumArticleSecondComment> getByCommentId(Long cmtId);
}
