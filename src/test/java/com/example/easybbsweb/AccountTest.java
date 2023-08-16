package com.example.easybbsweb;

import com.example.easybbsweb.domain.IdSelector;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.mapper.UserMainMapper;
import com.example.easybbsweb.utils.GenerateIdUtils;
import com.example.easybbsweb.utils.RedisGenerateIdUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
@Slf4j
public class AccountTest {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserMainMapper userMainMapper;
    @Resource
    RedisGenerateIdUtils redisGenerateIdUtils;
    @Test
    public void testInsertUser1(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(redisGenerateIdUtils.nextId(RedisGenerateIdUtils.USER));
        userInfo.setEmail("testInser1@qq.com");
        userInfo.setPassword(DigestUtils.md5DigestAsHex("aa123123.".getBytes()));
        userInfoMapper.insertSelective(userInfo);
    }

    @Test
    public void testInsertUser2(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(GenerateIdUtils.generateIdByEntity(IdSelector.USER));
        userInfo.setEmail("testInser1@qq.com");
        userInfo.setPassword("testInsertpwd");
//        userMainMapper.insert(userInfo);
    }
}
