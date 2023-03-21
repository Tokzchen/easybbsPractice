package com.example.easybbsweb.mapper;


import com.example.easybbsweb.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserInfoMapper {

     UserInfo selectUserByEmail(String email);

     Integer updateUserByEmail(UserInfo userInfo);

     @Select("select username from user_info where user_id=#{userId}")
     UserInfo selectUserNickNameById(String userId);

     @Select("SELECT * FROM user_info where username=#{username}")
     UserInfo selectByUsername(String username);
     
     @Select("select * from user_info where user_id=#{userId}")
     UserInfo selectByUserId(String userId);

     Integer insertUser(UserInfo userInfo);
}
