package com.example.easybbsweb.mapper;


import com.example.easybbsweb.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserInfoMapper {
     List<UserInfo> selectAll();

     Integer insertUser(UserInfo userInfo);

    //有需要改的就传值，没有则不传为空,但nickName必须得传
     Integer updateUserByNickName(UserInfo userInfo);
    //同理
     Integer updateUserByUserId(UserInfo userInfo);
    //同理
     Integer updateUserByEmail(UserInfo userInfo);

    Integer deleteUserByNickName(UserInfo userInfo);
    //同理
    Integer deleteUserByUserId(UserInfo userInfo);
    //同理
     Integer deleteUserByEmail(UserInfo userInfo);

    @Select("select * from user_info where id=#{id}")
     UserInfo selectById(String id);

    @Select("select count(*) from user_info")
    Integer selectCount();

    @Select("select * from user_info where nick_name=#{username}")
     UserInfo selectByUsername(String username);

    @Select("select * from user_info where email=#{email}")
     UserInfo selectUserByEmail(String email);

    List<UserInfo> selectCertainInfo(UserInfo userInfo);

    List<UserInfo> selectMultiInfo(UserInfo userInfo);
}
