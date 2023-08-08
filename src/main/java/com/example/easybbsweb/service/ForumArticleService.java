package com.example.easybbsweb.service;

import com.example.easybbsweb.controller.response.forum.FirstForumArticleResp;
import com.example.easybbsweb.controller.response.forum.ForumArticleRespFromES;
import com.example.easybbsweb.domain.entity.ForumArticle;


public interface ForumArticleService {
    public Integer getArticlesCountByUid(Long id);
    public FirstForumArticleResp getArticleAndCommentAndUserInfoById(Long id);
    public void publish(ForumArticle forumArticle, Long uid);

    public void update(ForumArticle forumArticle);
    public void deleteById(Long id);



    ForumArticleRespFromES SearchArticles(Long id, Object otherValue, Integer flag, String key);

    ForumArticleRespFromES searchMyArticles(Long id, Object otherValue, Integer flag, String key, Long currentUserId);

    ForumArticle getArticleById(Long id);
}
