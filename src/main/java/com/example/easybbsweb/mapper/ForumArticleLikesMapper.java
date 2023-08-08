package com.example.easybbsweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.easybbsweb.domain.entity.ForumArticleLikes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForumArticleLikesMapper extends BaseMapper<ForumArticleLikes> {
    // 这里无需编写任何方法，继承了 BaseMapper 就已经包含了基本的 CRUD 方法
}