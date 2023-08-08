package com.example.easybbsweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.easybbsweb.domain.entity.UserInfo;
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

    List<UserInfo> selectBatchByIds(List<Long> ids);

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