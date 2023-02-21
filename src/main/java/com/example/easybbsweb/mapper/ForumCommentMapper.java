package com.example.easybbsweb.mapper;

import com.example.easybbsweb.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ForumCommentMapper {

  List<Comment> selectCommentsByArticleId(String articleId);

  List<Comment> selectSecondCommentsBypBoardId(Comment comment);

}
