package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.Article;

import java.util.List;

public interface ForumArticalService {
    public boolean addArtical(Article article);

    //必须提供文章的id
    public boolean updateArtical(Article article);

    public List<Article> selectArticalAll(Integer page);

    public List<Article> selectArticalBoard(Integer page, String board);


    public boolean banArtical(Article article);
}
