package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.others.PageInfo;

import java.util.List;

public interface ForumArticalService {
    public boolean addArtical(Article article);

    //必须提供文章的id
    public boolean updateArtical(Article article);

    public PageInfo selectArticalAll(Integer page);

    public PageInfo selectArticalBoard(Integer page, String board);


    public boolean banArtical(Article article);

    public Article getArticleDetail(String articleId);
}
