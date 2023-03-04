package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.others.PageInfo;

import java.util.List;

public interface ForumArticalService {
    public boolean addArtical(Article article);

    //必须提供文章的id
    public boolean updateArtical(Article article);

    public PageInfo selectArticalAll(Integer page,Integer orderType);

    public PageInfo searchArticleAll(Integer page,Article article);

    public PageInfo selectArticalBoard(Integer page, String board);


    PageInfo selectArticleBoardWithOrder(Integer page, Article article);
    PageInfo searchArticleBoardWithOrder(Integer page,Article article);

    public boolean banArtical(Article article);

    public Article getArticleDetail(String articleId);

    public boolean articleDoLike(Article article);

    boolean postArticle(Article article);
}
