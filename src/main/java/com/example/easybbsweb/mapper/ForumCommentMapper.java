package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ForumCommentMapper {

  List<Comment> selectCommentsByArticleId(String articleId);

  List<Comment> selectSecondCommentsBypBoardId(Comment comment);

  Integer insertIntoComment(Comment comment);


  @Select("select count(*) from forum_comment where article_id=#{articleId}")
  public Integer selectArticleCommentCount(String articleId);

}
