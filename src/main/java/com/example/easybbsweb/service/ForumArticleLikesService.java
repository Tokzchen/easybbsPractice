package com.example.easybbsweb.service;

public interface ForumArticleLikesService {
    boolean checkLike(Long uid,Long articleId);
    void like(Long uid, Long articleId);
    void unLike(Long uid,Long articleId);




}
