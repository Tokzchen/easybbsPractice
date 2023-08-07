package com.example.easybbsweb.service;

public interface ForumArticleSecondCommentLikesService {
    boolean checkLike(Long uid,Long scmtId);
    void Like(Long uid,Long scmtId);
    void unLike(Long uid,Long scmtId);
}
