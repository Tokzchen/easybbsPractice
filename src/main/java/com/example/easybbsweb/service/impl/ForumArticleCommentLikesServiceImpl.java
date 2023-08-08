package com.example.easybbsweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.easybbsweb.mapper.ForumArticleCommentLikesMapper;
import com.example.easybbsweb.mapper.ForumArticleCommentMapper;
import com.example.easybbsweb.domain.entity.ForumArticleCommentLikes;
import com.example.easybbsweb.service.ForumArticleCommentLikesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class ForumArticleCommentLikesServiceImpl implements ForumArticleCommentLikesService {
    @Resource
    ForumArticleCommentLikesMapper likesMapper;
    @Resource
    ForumArticleCommentMapper mapper;
    ReentrantLock lock = new ReentrantLock();
    @Override
    public boolean checkLike(Long uid, Long cmtId) {

        return likesMapper.exists(new QueryWrapper<ForumArticleCommentLikes>()
                .eq("uid", uid).eq("cmt_id", cmtId));

    }


    @Override
    public void like(Long uid, Long cmtId) {


            ForumArticleCommentLikes entity = new ForumArticleCommentLikes()
                    .setUid(uid)
                    .setCmtId(cmtId);
            mapper.updateLikeById(cmtId,1);
            likesMapper.insert(entity);

    }

    @Override
    public void unLike(Long uid, Long cmtId) {

            likesMapper.delete(new QueryWrapper<ForumArticleCommentLikes>()
                    .eq("uid", uid).eq("cmtId", cmtId));
            mapper.updateLikeById(cmtId,-1);


    }
}
