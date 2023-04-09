package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.TestRecord;
import com.example.easybbsweb.domain.entity.TestRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestRecordMapper {
    long countByExample(TestRecordExample example);

    int deleteByExample(TestRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestRecord record);

    int insertSelective(TestRecord record);

    List<TestRecord> selectByExample(TestRecordExample example);

    TestRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TestRecord record, @Param("example") TestRecordExample example);

    int updateByExample(@Param("record") TestRecord record, @Param("example") TestRecordExample example);

    int updateByPrimaryKeySelective(TestRecord record);

    int updateByPrimaryKey(TestRecord record);
}