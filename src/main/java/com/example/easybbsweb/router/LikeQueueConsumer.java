package com.example.easybbsweb.router;

import com.example.easybbsweb.common.RabbitMQConstants;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.example.easybbsweb.domain.entity.ForumArticleLikes;
import com.example.easybbsweb.repository.ForumArticleElasticSearchDAO;
import com.example.easybbsweb.router.message.LikeMessage;
import com.example.easybbsweb.router.message.LikeMessageES;
import com.example.easybbsweb.service.ForumArticleLikesService;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class LikeQueueConsumer {
    @Resource
    ForumArticleLikesService forumArticleLikesService;
    @RabbitListener(queues = RabbitMQConstants.LIKE_LIKE_QUEUE)
    public void save(LikeMessage likeMessage){
        forumArticleLikesService.likeOrUnlike(likeMessage.getId(),likeMessage.getUid(),likeMessage.isLike(),likeMessage.getIdentifies());
    }
}