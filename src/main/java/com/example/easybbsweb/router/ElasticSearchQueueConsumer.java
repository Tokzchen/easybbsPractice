package com.example.easybbsweb.router;

import com.example.easybbsweb.common.RabbitMQConstants;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.example.easybbsweb.repository.ForumArticleElasticSearchDAO;
import com.example.easybbsweb.router.message.LikeMessageES;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ElasticSearchQueueConsumer {
    @Resource
    ForumArticleElasticSearchDAO elasticSearchDAO;
    @RabbitListener(queues = RabbitMQConstants.ES_SAVE_QUEUE)
    public void save(ForumArticle forumArticle){
        ForumArticle forumArticleES = new ForumArticle();
        BeanUtils.copyProperties(forumArticle,forumArticleES);
        elasticSearchDAO.save(forumArticleES);
    }
    @RabbitListener(queues = RabbitMQConstants.ES_DELETE_QUEUE)
    public void delete(String id){
        elasticSearchDAO.deleteById(id);
    }

    @RabbitListener(queues = RabbitMQConstants.ES_UPDATE_LIKE_QUEUE)
    public void updateLike(LikeMessageES likeMessageES){
        elasticSearchDAO.updateLike(likeMessageES.getId(), likeMessageES.getLikeCount());
    }
}
