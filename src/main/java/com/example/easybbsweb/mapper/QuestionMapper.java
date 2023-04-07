package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.Question;
import com.example.easybbsweb.domain.entity.QuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapper {
    long countByExample(QuestionExample example);

    int deleteByExample(QuestionExample example);

    int deleteByPrimaryKey(Integer queId);

    int insert(Question record);

    int insertSelective(Question record);

    List<Question> selectByExample(QuestionExample example);

    Question selectByPrimaryKey(Integer queId);

    int updateByExampleSelective(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExample(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);
}