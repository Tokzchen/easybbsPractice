package com.example.easybbsweb;

import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.others.LoginStatus;
import com.example.easybbsweb.domain.others.Sex;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.utils.GenerateIdUtils;
import org.apache.catalina.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@SpringBootTest
class EasybbsWebApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Test
    void contextLoads() {
        List<UserInfo> userInfos = userInfoMapper.selectAll();
        System.out.println(userInfos);
    }

    @Test
    void testMapper_insert(){
        Date date = new Date();
        //一般在于注册时
        UserInfo userInfo1 = new UserInfo(GenerateIdUtils.generateID(), "testInterface", "18506675882@163.com", "1234", Sex.MALE, null, date, date, "127.0.0.1", "广州", 0, 0, LoginStatus.NORMAL,null,null   );
        Integer integer = userInfoMapper.insertUser(userInfo1);
        List<UserInfo> userInfos = userInfoMapper.selectAll();
        System.out.println(userInfos);
    }

    @Test
    public void testDelete(){
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName("testInterface");
        Integer integer = userInfoMapper.deleteUserByNickName(userInfo);
        if(integer>0){
            System.out.println("删除成功...");
        }
    }


    @Test
    void testSelectCertain(){
        UserInfo userInfo = new UserInfo();
        userInfo.setSex(Sex.MALE);

        List<UserInfo> password1 = userInfoMapper.selectCertainInfo(userInfo);
        System.out.println(password1);
    }



}
