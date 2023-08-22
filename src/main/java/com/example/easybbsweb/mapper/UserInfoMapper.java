package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.entity.UserInfoExample;
import java.util.List;

import com.example.easybbsweb.domain.entity.dto.UserDTO1;
import com.example.easybbsweb.domain.entity.dto.UserDTO2;
import com.example.easybbsweb.domain.entity.dto.UserJoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper  {
    long countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(Long userId);

    List<UserInfo> selectBatchIds(List<Long> ids);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List<UserDTO2> selectUserLawAidInfoToConfirm(Long uniId);

    List<UserDTO2> selectUserLawAidInfoConfirmed(Long uniId);
}