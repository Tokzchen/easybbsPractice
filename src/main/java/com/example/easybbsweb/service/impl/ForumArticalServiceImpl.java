package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.others.ArticleStatus;
import com.example.easybbsweb.domain.others.PageInfo;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.mapper.ForumArticalMapper;
import com.example.easybbsweb.service.ForumArticalService;
import com.example.easybbsweb.utils.GenerateIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumArticalServiceImpl implements ForumArticalService {
    @Autowired
    ForumArticalMapper forumArticalMapper;
    @Override
    public boolean addArtical(Article article) {
        article.setArticleId(GenerateIdUtils.generateID());
        Integer integer = forumArticalMapper.insertArtical(article);
        if(integer>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateArtical(Article article) {
        if(article.getArticleId()==null){
            throw new  BusinessException("修改的文章必须存在id");
        }
        Integer integer = forumArticalMapper.updateArticalByArticalId(article);
        if(integer>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public PageInfo selectArticalAll(Integer page) {
        PageInfo pageInfo = new PageInfo();
        //查出来了所有的文章
        List<Article> articles = forumArticalMapper.selectAllModules();
        pageInfo.setPageNo(page);
        pageInfo.setTotalCnt(articles.size());
        //下面进行分页
        List<Article> returnArticles=new ArrayList<>();
        Integer index=(page-1)*4;
        for(int i=index;i<=index+3;i++){
            //处理最后一页的数据可能不够一页
            if(i>articles.size()-1){
                break;
            }
            returnArticles.add(articles.get(i));
        }
        pageInfo.setList(returnArticles);
        return pageInfo;
    }

    @Override
    public PageInfo selectArticalBoard(Integer page, String board) {
        PageInfo pageInfo = new PageInfo();
        Integer row=(page-1)*4;
        //这里是查出了这个模块的所有文章
        List<Article> articles = forumArticalMapper.selectBoards(board);
        pageInfo.setPageNo(page);
        pageInfo.setTotalCnt(articles.size());
        List<Article> returnArticles=new ArrayList<>();
        //下面进行分页
        for(int i=row;i<=row+3;i++){
            //处理最后一页的数据可能不够一页
            if(i>articles.size()-1){
                break;
            }
            returnArticles.add(articles.get(i));
        }
        //对文章的content进行抹除瘦身，加快传递速度
        for(Article a:returnArticles){
            a.loseWeight();
        }
        pageInfo.setList(returnArticles);
        return pageInfo;
    }

    @Override
    public boolean banArtical(Article article) {
        if(article.getArticleId()==null){
            throw new BusinessException("需要指定文章id进行屏蔽...");
        }
        article.setStatus(ArticleStatus.EXAMINING);
        Integer integer = forumArticalMapper.updateArticalByArticalId(article);
        if(integer>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Article getArticleDetail(String articleId) {
        Article article = forumArticalMapper.selectSingle(articleId);
        return article;
    }
}
