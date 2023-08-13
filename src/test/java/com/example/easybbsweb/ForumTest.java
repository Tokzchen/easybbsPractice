package com.example.easybbsweb;

import com.example.easybbsweb.controller.response.forum.SecondCommentResp;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.example.easybbsweb.mapper.ForumArticleMapper;
import com.example.easybbsweb.service.impl.ForumArticleSecondCommentServiceImpl;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.annotation.Resource;
import org.apache.el.parser.Token;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ForumTest {
    @Resource
    ForumArticleMapper mapper;
    @Resource
    ForumArticleSecondCommentServiceImpl forumArticleSecondCommentService;
//    @Test
//    void func1() throws IOException {
//        ForumArticle forumArticle = new ForumArticle("1","@","ouo","您吃了嘛",new Date().getTime(),0,1);
//        bookRepository.show("d");
//    }
//
    @Test
    void func2() throws IOException {
        List<ForumArticle> forumArticles = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            ForumArticle forumArticle = new ForumArticle().setId((long) i);
            forumArticles.add(forumArticle);
        }
        mapper.t(forumArticles.get(1));
    }
}
