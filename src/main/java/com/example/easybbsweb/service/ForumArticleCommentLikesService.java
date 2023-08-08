package com.example.easybbsweb.service;

public interface ForumArticleCommentLikesService {
    boolean checkLike(Long uid,Long cmtId);


    void like(Long uid, Long cmtId);
    void unLike(Long uid,Long cmtId);


}
