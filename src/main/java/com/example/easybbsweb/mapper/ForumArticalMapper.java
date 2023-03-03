package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
//规定每次只查出四条数据

@Mapper
@Repository
public interface ForumArticalMapper {
    //第一页0,第二页4,第三页8
    @Select("select * from forum_article order by last_update_time desc")
    public List<Article> selectAllModules();

    public List<Article> searchAllModules(Article article);

    @Select("select * from forum_article order by post_time desc")
    public List<Article> selectAllOrderedPostTime();

    @Select("select *,(good_count+read_count+comment_count) hot_count from forum_article order by hot_count desc")
    public List<Article> selectAllOrderedHottest();

    public List<Article> selectAllModulesOrdered(Article article);
    @Select("select * from forum_article where article_id=#{articleId}")
    public Article selectSingle(String articleId);

    @Select("select * from forum_article where p_board_name=#{board}")
    public List<Article> selectBoards(String board);

    public  List<Article> selectBoardsWithOrder(Article article);

    public Integer insertArtical(Article article);

    public Integer updateArticalByArticalId(Article article);

    @Update("update forum_article set read_count=read_count+1 where article_id=#{articleId}")
    public Integer increaseReadCountByOne(String articleId);

    @Update("update forum_article set good_count=good_count+1 where article_id=#{articleId}")
    public Integer increaseGoodCountByOne(String articleId);

}
