package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ForumBoardMapper {
    @Select("select * from forum_board where p_board_id=#{id}")
    List<Board> selectByPBoardId(String id);
}
