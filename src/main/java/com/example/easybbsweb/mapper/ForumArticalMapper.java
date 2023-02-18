package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
//规定每次只查出四条数据

@Mapper
@Repository
public interface ForumArticalMapper {
    //第一页0,第二页4,第三页8
    @Select("select * from forum_article")
    public List<Article> selectAllModules();

    @Select("select * from forum_article where p_board_name=#{board}")
    public List<Article> selectBoards(String board);

    public Integer insertArtical(Article article);

    public Integer updateArticalByArticalId(Article article);

}
