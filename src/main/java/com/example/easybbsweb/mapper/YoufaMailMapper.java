package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.YoufaMail;
import com.example.easybbsweb.domain.entity.YoufaMailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface YoufaMailMapper {
    long countByExample(YoufaMailExample example);

    int deleteByExample(YoufaMailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(YoufaMail record);

    int insertSelective(YoufaMail record);

    List<YoufaMail> selectByExampleWithBLOBs(YoufaMailExample example);

    List<YoufaMail> selectByExample(YoufaMailExample example);

    YoufaMail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") YoufaMail record, @Param("example") YoufaMailExample example);

    int updateByExampleWithBLOBs(@Param("record") YoufaMail record, @Param("example") YoufaMailExample example);

    int updateByExample(@Param("record") YoufaMail record, @Param("example") YoufaMailExample example);

    int updateByPrimaryKeySelective(YoufaMail record);

    int updateByPrimaryKeyWithBLOBs(YoufaMail record);

    int updateByPrimaryKey(YoufaMail record);

    List<YoufaMail> getUserMails(Long uid);

    boolean insertMultipleSelective(List<YoufaMail> mails);
}