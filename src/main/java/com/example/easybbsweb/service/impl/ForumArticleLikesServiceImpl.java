package com.example.easybbsweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.easybbsweb.common.RabbitMQConstants;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.example.easybbsweb.mapper.ForumArticleLikesMapper;
import com.example.easybbsweb.mapper.ForumArticleMapper;
import com.example.easybbsweb.domain.entity.ForumArticleLikes;
import com.example.easybbsweb.router.LikeMessage;
import com.example.easybbsweb.service.ForumArticleLikesService;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ForumArticleLikesServiceImpl implements ForumArticleLikesService {
    @Resource
    ForumArticleLikesMapper likeMapper;
    @Resource
    ForumArticleMapper mapper;
    @Resource
    RabbitTemplate rabbitTemplate;
    @Override
    public boolean checkLike(Long uid, Long scmtId) {

        return likeMapper.exists(new QueryWrapper<ForumArticleLikes>()
                .eq("uid", uid).eq("article_id", scmtId));

    }

    @Override
    public void like(Long uid, Long id) {
        ForumArticleLikes entity = new ForumArticleLikes()
                .setUid(uid)
                .setArticleId(id);
        Integer likeCount = mapper.selectById(id).getLike();
        likeCount++;
        mapper.updateById(new ForumArticle().setId(id).setLike(likeCount));
        rabbitTemplate.convertAndSend(RabbitMQConstants.ES_EXCHANGE,RabbitMQConstants.ES_UPDATE_LIKE_KEY,new LikeMessage(id,likeCount));
        likeMapper.insert(entity);
    }

    @Override
    public void unLike(Long uid, Long id) {
        ForumArticleLikes entity = new ForumArticleLikes()
                .setUid(uid)
                .setArticleId(id);
        Integer likeCount = mapper.selectById(id).getLike();
        likeCount--;
        mapper.updateById(new ForumArticle().setId(id).setLike(likeCount));
        rabbitTemplate.convertAndSend(RabbitMQConstants.ES_EXCHANGE,RabbitMQConstants.ES_UPDATE_LIKE_KEY,new LikeMessage(id,likeCount));
        likeMapper.deleteById(entity);
    }



}
