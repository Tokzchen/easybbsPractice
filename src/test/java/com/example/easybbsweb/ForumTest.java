package com.example.easybbsweb;

import com.example.easybbsweb.service.impl.ForumArticleService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ForumTest {
    @Resource
    ForumArticleService forumArticleService;
    @Test
    void func1(){
//        forumArticleService.html2article("");
    }
}
