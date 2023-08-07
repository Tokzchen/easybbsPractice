package com.example.easybbsweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.easybbsweb.controller.response.forum.CommentResp;
import com.example.easybbsweb.mapper.ForumArticleCommentMapper;
import com.example.easybbsweb.domain.entity.ForumArticleComment;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.service.ForumArticleCommentService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumArticleCommentServiceImpl extends ServiceImpl<ForumArticleCommentMapper, ForumArticleComment> implements ForumArticleCommentService {

    @Resource
    ForumArticleCommentMapper mapper;
    @Resource
    UserInfoMapper userInfoMapper;


    @Override
    public CommentResp publish(Long uid, CommentResp commentResp) {
        ForumArticleComment comment = new ForumArticleComment();
        BeanUtils.copyProperties(commentResp,comment);
        comment.setUid(uid);
        mapper.insert(comment);
        commentResp.setId(comment.getId());
        commentResp.setUser(userInfoMapper.selectByPrimaryKey(comment.getUid()));
        return commentResp;

    }

    @Override
    public void update(ForumArticleComment forumArticle) {
        mapper.updateById(forumArticle);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    public List<ForumArticleComment> getAllByArticleId(Long articleId) {
        return mapper.selectList(new QueryWrapper<ForumArticleComment>().eq("article_id", articleId));
    }

    @Override
    public List<ForumArticleComment> getPage(Long articleId, long from, long to) {
        IPage<ForumArticleComment> page = new Page<>(from, to);
        QueryWrapper<ForumArticleComment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        IPage<ForumArticleComment> userPage = mapper.selectPage(page, wrapper);
        // 获取分页查询结果
        return userPage.getRecords();
    }

    @Override
    public List<ForumArticleComment> getByArticleId(Long articleId) {
        QueryWrapper<ForumArticleComment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);
        return mapper.selectList(wrapper);
    }

}
