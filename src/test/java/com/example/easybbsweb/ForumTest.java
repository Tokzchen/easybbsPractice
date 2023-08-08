package com.example.easybbsweb;

import com.example.easybbsweb.controller.response.forum.SecondCommentResp;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.example.easybbsweb.service.impl.ForumArticleSecondCommentServiceImpl;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.annotation.Resource;
import org.apache.el.parser.Token;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ForumTest {

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
        SecondCommentResp secondCommentResp = new SecondCommentResp();
        forumArticleSecondCommentService.publish(101690910259505001L,secondCommentResp);
        System.out.println(secondCommentResp);
    }
}
