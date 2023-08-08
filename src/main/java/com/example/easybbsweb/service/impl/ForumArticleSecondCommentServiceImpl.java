package com.example.easybbsweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.easybbsweb.controller.response.forum.SecondCommentResp;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.mapper.ForumArticleSecondCommentMapper;
import com.example.easybbsweb.domain.entity.ForumArticleSecondComment;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.service.ForumArticleSecondCommentService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumArticleSecondCommentServiceImpl extends ServiceImpl<ForumArticleSecondCommentMapper, ForumArticleSecondComment> implements ForumArticleSecondCommentService {


    @Resource
    ForumArticleSecondCommentMapper mapper;
    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public SecondCommentResp publish(Long uid, SecondCommentResp secondCommentResp) {  // 获取id  user
        ForumArticleSecondComment secondComment = new ForumArticleSecondComment();
        BeanUtils.copyProperties(secondCommentResp, secondComment);
        secondComment.setUid(uid);   // uid
        UserInfo replyUser = secondCommentResp.getReplyUser();
        if (replyUser != null) {
            secondComment.setReplyUid(secondCommentResp.getReplyUser().getUserId());  // replyUid
            secondCommentResp.setReplyUser(userInfoMapper.selectByPrimaryKey(secondComment.getReplyUid()));
        }
        mapper.insert(secondComment);
        secondCommentResp.setId(secondComment.getId());   // id
        secondCommentResp.setUser(userInfoMapper.selectByPrimaryKey(secondComment.getUid())); // user
        return secondCommentResp;
    }

    @Override
    public void update(ForumArticleSecondComment secondComment) {
        mapper.updateById(secondComment);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteById(id);
    }

    @Override
    public List<ForumArticleSecondComment> getByCommentId(Long cmtId) {
        QueryWrapper<ForumArticleSecondComment> wrapper = new QueryWrapper<>();
        wrapper.eq("cmt_id", cmtId);
        return mapper.selectList(wrapper);
    }
}
