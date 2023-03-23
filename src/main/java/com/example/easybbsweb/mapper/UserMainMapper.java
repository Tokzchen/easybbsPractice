package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.UserMain;
import com.example.easybbsweb.domain.entity.UserMainExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMainMapper {
    long countByExample(UserMainExample example);

    int deleteByExample(UserMainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserMain record);

    int insertSelective(UserMain record);

    List<UserMain> selectByExample(UserMainExample example);

    UserMain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserMain record, @Param("example") UserMainExample example);

    int updateByExample(@Param("record") UserMain record, @Param("example") UserMainExample example);

    int updateByPrimaryKeySelective(UserMain record);

    int updateByPrimaryKey(UserMain record);
}