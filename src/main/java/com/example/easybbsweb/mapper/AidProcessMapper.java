package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.AidProcess;
import com.example.easybbsweb.domain.entity.AidProcessExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AidProcessMapper {
    long countByExample(AidProcessExample example);

    int deleteByExample(AidProcessExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AidProcess record);

    int insertSelective(AidProcess record);

    List<AidProcess> selectByExample(AidProcessExample example);

    AidProcess selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AidProcess record, @Param("example") AidProcessExample example);

    int updateByExample(@Param("record") AidProcess record, @Param("example") AidProcessExample example);

    int updateByPrimaryKeySelective(AidProcess record);

    int updateByPrimaryKey(AidProcess record);
}