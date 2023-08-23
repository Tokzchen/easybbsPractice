package com.example.easybbsweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.easybbsweb.anotation.IdentifiesCheck;
import com.example.easybbsweb.common.RabbitMQConstants;
import com.example.easybbsweb.common.RedisPrefixConstants;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.example.easybbsweb.mapper.ForumArticleLikesMapper;
import com.example.easybbsweb.mapper.ForumArticleMapper;
import com.example.easybbsweb.domain.entity.ForumArticleLikes;
import com.example.easybbsweb.router.message.LikeMessage;
import com.example.easybbsweb.router.message.LikeMessageES;
import com.example.easybbsweb.service.ForumArticleLikesService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class ForumArticleLikesServiceImpl implements ForumArticleLikesService {
    @Resource
    ForumArticleLikesMapper likeMapper;
    @Resource
    ForumArticleMapper mapper;
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    RedisTemplate<String, String> redisTemplate;

    Integer keyLST = 60; // 存活时间

    ReentrantLock lock = new ReentrantLock();

    @Override
    public boolean checkLike(Long uid, Long scmtId) {

        return likeMapper.exists(new QueryWrapper<ForumArticleLikes>()
                .eq("uid", uid).eq("article_id", scmtId));

    }

    // 防止同步阻塞
    @Override
    public void sendLike(Long uid, Long id, Boolean like, String identifies) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.ES_EXCHANGE, RabbitMQConstants.LIKE_LIKE_KEY, new LikeMessage(uid, id, like, identifies));
    }

    @Override
    @IdentifiesCheck(RedisPrefixConstants.FORUM_ARTICLE)
    public void likeOrUnlike(Long uid, Long id, Boolean like, String identifies) {
        log.debug("到达like消费者");
        lock.lock();

        try {
            ForumArticleLikes entity = new ForumArticleLikes()
                    .setUid(uid)
                    .setArticleId(id);
            Integer likeCount = mapper.selectById(id).getLike();
            if (like) {
                likeCount++;
                likeMapper.insert(entity); // 插入数据
            } else {
                likeCount--;
                likeMapper.delete(new QueryWrapper<ForumArticleLikes>().eq("uid", entity.getUid())
                        .eq("article_id", entity.getArticleId()));
            }
            log.debug(likeCount.toString());
            mapper.updateById(new ForumArticle().setId(id).setLike(likeCount));  // mysql数据库
            rabbitTemplate.convertAndSend(RabbitMQConstants.ES_EXCHANGE, RabbitMQConstants.ES_UPDATE_LIKE_KEY, new LikeMessageES(id, likeCount)); // es数据库
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        } finally {

            lock.unlock();
        }

    }

    @Override
    public void identifies(String identifies) {
        System.out.println(identifies);
        String key = RedisPrefixConstants.FORUM_ARTICLE + "identifies";
        redisTemplate.opsForValue().set(key, identifies, keyLST, TimeUnit.SECONDS);
    }
}
