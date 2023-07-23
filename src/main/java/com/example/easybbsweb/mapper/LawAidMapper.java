package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.LawAid;
import com.example.easybbsweb.domain.entity.LawAidExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LawAidMapper {
    long countByExample(LawAidExample example);

    int deleteByExample(LawAidExample example);

    int deleteByPrimaryKey(Long lawAidId);

    int insert(LawAid record);

    int insertSelective(LawAid record);

    List<LawAid> selectByExampleWithBLOBs(LawAidExample example);

    List<LawAid> selectByExample(LawAidExample example);

    LawAid selectByPrimaryKey(Long lawAidId);

    int updateByExampleSelective(@Param("record") LawAid record, @Param("example") LawAidExample example);

    int updateByExampleWithBLOBs(@Param("record") LawAid record, @Param("example") LawAidExample example);

    int updateByExample(@Param("record") LawAid record, @Param("example") LawAidExample example);

    int updateByPrimaryKeySelective(LawAid record);

    int updateByPrimaryKeyWithBLOBs(LawAid record);

    int updateByPrimaryKey(LawAid record);
}