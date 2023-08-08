package com.example.easybbsweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.easybbsweb.controller.response.forum.SecondCommentResp;
import com.example.easybbsweb.domain.entity.ForumArticleSecondComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ForumArticleSecondCommentMapper extends BaseMapper<ForumArticleSecondComment> {
    // 这里无需编写任何方法，继承了 BaseMapper 就已经包含了基本的 CRUD 方法
    void updateLikeById(@Param("articleId") Long articleId, @Param("increment") int increment);


}