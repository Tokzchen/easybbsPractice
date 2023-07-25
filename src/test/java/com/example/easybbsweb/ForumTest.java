package com.example.easybbsweb;

import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.service.impl.ForumArticleService;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ForumTest {
    @Resource
    ForumArticleService forumArticleService;

    @Resource
    AccountService accountService;
    @Test
    void func1(){
//        /
        TokenUtil.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyT3JVbmlJZCI6IjEwMTY4OTc2NzI5NTY5MjAwMSIsImlzcyI6ImF1dGgwIiwiZXhwIjoxNjkwMDQ4Mzk3LCJlbWFpbCI6IjIxNTU1MDcxNTFAcXEuY29tIn0.rvP4Y-zf6VzNnOuWrVL3WW1S0XYyTS10z23CtjWUjik");

    }

    @Test
    void test2(){
        List<UserInfo> allUser = accountService.getAllUser();
        System.out.println(allUser);
    }


}
