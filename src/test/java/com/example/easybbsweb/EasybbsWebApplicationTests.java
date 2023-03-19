package com.example.easybbsweb;

import com.example.easybbsweb.domain.others.LoginStatus;
import com.example.easybbsweb.domain.others.Sex;
import com.example.easybbsweb.mapper.ForumArticalMapper;
import com.example.easybbsweb.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class EasybbsWebApplicationTests {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    ForumArticalMapper forumArticalMapper;
    @Test
    void contextLoads() {
        List<UserInfo> userInfos = userInfoMapper.selectAll();
        System.out.println(userInfos);
    }

//    @Test
//    void testMapper_insert(){
//        Date date = new Date();
//        //一般在于注册时
//        UserInfo userInfo1 = new UserInfo(GenerateIdUtils.generateID(), "testInterface", "18506675882@163.com", "1234", Sex.MALE, null, date, date, "127.0.0.1", "广州", 0, 0, LoginStatus.NORMAL,null,null   );
//        Integer integer = userInfoMapper.insertUser(userInfo1);
//        List<UserInfo> userInfos = userInfoMapper.selectAll();
//        System.out.println(userInfos);
//    }
    @Test
    public void testSearchAll(){
        Article article = new Article();
        String keyWord="金庸";
        StringBuilder stringBuilder = new StringBuilder(keyWord);
        stringBuilder.append("%");
        stringBuilder.insert(0,"%");
        article.setKeyWord(stringBuilder.toString());
        List<Article> articles = forumArticalMapper.searchAllModules(article);
        log.info("含有关键字金庸的列表是{}",articles);
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

    @Test
    void testOrderedArticle(){
        List<Article> articles = forumArticalMapper.selectAllOrderedPostTime();
        log.info("按照发布时间排序是 ",articles);
        List<Article> articles1 = forumArticalMapper.selectAllOrderedHottest();
        log.info("按照热度，点赞数+阅读量+点击率得到的排序是",articles1);
    }



}
