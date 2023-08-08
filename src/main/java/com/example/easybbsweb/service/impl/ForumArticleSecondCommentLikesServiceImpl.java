package com.example.easybbsweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.easybbsweb.mapper.ForumArticleSecondCommentLikesMapper;
import com.example.easybbsweb.mapper.ForumArticleSecondCommentMapper;
import com.example.easybbsweb.domain.entity.ForumArticleSecondCommentLikes;
import com.example.easybbsweb.service.ForumArticleSecondCommentLikesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ForumArticleSecondCommentLikesServiceImpl implements ForumArticleSecondCommentLikesService {
    @Resource
    ForumArticleSecondCommentLikesMapper likesMapper;
    @Resource
    ForumArticleSecondCommentMapper mapper;
    ReentrantLock lock = new ReentrantLock();
    @Override
    public boolean checkLike(Long uid, Long scmtId) {

        return likesMapper.exists(new QueryWrapper<ForumArticleSecondCommentLikes>()
                .eq("uid", uid).eq("scmt_id", scmtId));

    }

    @Override
    public void Like(Long uid, Long scmtId) {
        lock.lock();
        try {
            ForumArticleSecondCommentLikes entity = new ForumArticleSecondCommentLikes()
                    .setUid(uid)
                    .setScmtId(scmtId);
            mapper.updateLikeById(scmtId,1);
            likesMapper.insert(entity);
        } finally {

            lock.unlock();
        }
    }

    @Override
    public void unLike(Long uid, Long scmtId) {
        lock.lock();
        try {
            likesMapper.delete(new QueryWrapper<ForumArticleSecondCommentLikes>()
                    .eq("uid", uid).eq("scmtId", scmtId));
            mapper.updateLikeById(scmtId,-1);
        } finally {
            lock.unlock();
        }
    }
}
