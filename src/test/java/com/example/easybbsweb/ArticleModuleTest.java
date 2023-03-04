package com.example.easybbsweb;


import com.example.easybbsweb.domain.entity.Article;
import com.example.easybbsweb.domain.others.PageInfo;
import com.example.easybbsweb.mapper.ForumArticalMapper;
import com.example.easybbsweb.service.ForumArticalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class ArticleModuleTest {
    @Autowired
    ForumArticalService forumArticalService;

//    @Test
//    @DisplayName("测试所有板块文章的分页查询")
//    public void testSelectArtical(){
//        List<Article> articles = forumArticalService.selectArticalAll(1);
//        System.out.println(articles);
//    }
//
//    @Test
//    @DisplayName("测试特定板块的分页查询")
//    public void testSelectArticalCertain(){
//        List<Article> articles = forumArticalService.selectArticalBoard(1, "前端");
//        System.out.println(articles);
//    }

    @Test
    @DisplayName("测试在特定模块搜索文章功能")
    public void testSearch(){
        Article article = new Article();
        article.setpBoardName("前端");
        article.setKeyWord("条件");
        PageInfo pageInfo = forumArticalService.searchArticleBoardWithOrder(1, article);
        System.out.println(pageInfo);
    }

    @Test
    @DisplayName("测试上传帖子")
    public void testUploadPost(){
        Article article = new Article();
    }
}
