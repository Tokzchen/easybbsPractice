package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMainMapper {

    Integer insertUser(UserInfo userInfo);
}
