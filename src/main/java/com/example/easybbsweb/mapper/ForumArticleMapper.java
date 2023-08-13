package com.example.easybbsweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.easybbsweb.controller.response.forum.FirstForumArticleResp;
import com.example.easybbsweb.domain.entity.ForumArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumArticleMapper extends BaseMapper<ForumArticle> {
    // 这里无需编写任何方法，继承了 BaseMapper 就已经包含了基本的 CRUD 方法
    void updateLikeById(@Param("articleId") Long articleId, @Param("increment") int increment);
    void t(  @Param("list") ForumArticle  list);

}