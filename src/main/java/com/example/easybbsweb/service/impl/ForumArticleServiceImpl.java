package com.example.easybbsweb.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.easybbsweb.common.RabbitMQConstants;
import com.example.easybbsweb.controller.response.forum.CommentResp;
import com.example.easybbsweb.controller.response.forum.FirstForumArticleResp;
import com.example.easybbsweb.controller.response.forum.ForumArticleRespFromES;
import com.example.easybbsweb.controller.response.forum.SecondCommentResp;
import com.example.easybbsweb.domain.entity.*;
import com.example.easybbsweb.mapper.*;
import com.example.easybbsweb.repository.ForumArticleElasticSearchDAO;
import com.example.easybbsweb.service.ForumArticleService;
import jakarta.annotation.Resource;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ForumArticleServiceImpl implements ForumArticleService {
    @Resource
    ForumArticleMapper articleMapper;
    @Resource
    ForumArticleCommentMapper commentMapper;
    @Resource
    ForumArticleSecondCommentMapper secondCommentMapper;
    @Resource
    UserInfoMapper userInfoMapper;
    @Resource
    ForumArticleElasticSearchDAO elasticSearchDAO;
    @Value("${articleConfig.countPerPage}")
    Integer COUNT_PER_PAGE;
    @Resource
    RabbitTemplate rabbitTemplate;

    @Override
    public Integer getArticlesCountByUid(Long id) {
        QueryWrapper<ForumArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", id);
        return articleMapper.selectList(wrapper).size();
    }

    /**
     * @return 按照降序拿到like最多的10条评论
     */
    @Override
    public FirstForumArticleResp getArticleAndCommentAndUserInfoById(Long id) {  // 业务层处理分页

        FirstForumArticleResp forumArticleResp = new FirstForumArticleResp();
        ForumArticle forumArticle = articleMapper.selectById(id);  // 文章
        BeanUtils.copyProperties(forumArticle,forumArticleResp);
        forumArticleResp.setUser(userInfoMapper.selectByPrimaryKey(forumArticle.getUid())); // 文章作者


        QueryWrapper<ForumArticleComment> wrapper = new QueryWrapper<ForumArticleComment>().eq("article_id", forumArticle.getId());
        Page<ForumArticleComment> commentPage = new Page<>();
        commentPage.addOrder(OrderItem.desc("like")).setSize(10);
        Page<ForumArticleComment> comments = commentMapper.selectPage(commentPage, wrapper);

        List<Long> ids = comments.getRecords().stream().map(ForumArticleComment::getUid).collect(Collectors.toList());
        List<UserInfo> commentUsers = userInfoMapper.selectBatchIds(ids);
        Map<Long, UserInfo> commentUserMap = commentUsers.stream().collect(Collectors.toMap(UserInfo::getUserId, userInfo -> userInfo)); // userId:user

        List<CommentResp> commentResps = comments.getRecords().stream().map(comment -> {
            CommentResp commentResp = new CommentResp();
            BeanUtils.copyProperties(comment, commentResp);
            commentResp.setUser(commentUserMap.get(comment.getUid()));  // user

            List<ForumArticleSecondComment> secondComments = secondCommentMapper.selectByMap(Map.of("cmt_id", comment.getId()));
            List<Long> secondCommentUids = secondComments.stream().map(ForumArticleSecondComment::getUid).collect(Collectors.toList());

            List<Long> secondCommentReplyUids = secondComments.stream().map(ForumArticleSecondComment::getReplyUid).collect(Collectors.toList());

            Map<Long, UserInfo> secondCommentUserMap = userInfoMapper.selectBatchIds(secondCommentUids).stream().collect(Collectors.toMap(UserInfo::getUserId, userInfo -> userInfo));
            Map<Long, UserInfo> secondCommentReplyUserMap = userInfoMapper.selectBatchIds(secondCommentReplyUids).stream().collect(Collectors.toMap(UserInfo::getUserId, userInfo -> userInfo));
            List<SecondCommentResp> secondCommentResps = secondComments.stream().map(secondComment -> {
                SecondCommentResp secondCommentResp = new SecondCommentResp();
                BeanUtils.copyProperties(secondComment, secondCommentResp);
                secondCommentResp.setUser(secondCommentUserMap.get(secondComment.getUid())); // user

                secondCommentResp.setReplyUser(secondCommentReplyUserMap.get(secondComment.getReplyUid())); // replyUser

                return secondCommentResp;
            }).collect(Collectors.toList());

            commentResp.setSecondComments(secondCommentResps);  // secondCommentResps

            return commentResp;
        }).collect(Collectors.toList());


        forumArticleResp.setComments(commentResps);  // commentReps

        return forumArticleResp;
    }

    @Override
    public void publish(ForumArticle forumArticle, Long uid) {
        forumArticle.setUid(uid);
        articleMapper.insert(forumArticle);
        rabbitTemplate.convertAndSend(RabbitMQConstants.ES_EXCHANGE, RabbitMQConstants.ES_SAVE_KEY, articleMapper.selectById(forumArticle.getId()));
    }

    @Override
    public void update(ForumArticle forumArticle) {
        articleMapper.updateById(forumArticle);
        rabbitTemplate.convertAndSend(RabbitMQConstants.ES_EXCHANGE, RabbitMQConstants.ES_UPDATE_KEY, articleMapper.selectById(forumArticle.getId()));
    }

    @Override
    public void deleteById(Long id) {
        articleMapper.deleteById(id);
        rabbitTemplate.convertAndSend(RabbitMQConstants.ES_EXCHANGE, RabbitMQConstants.ES_DELETE_KEY, id);
    }

    @Override
    public ForumArticleRespFromES SearchArticles(Long id, Object otherValue, Integer flag, String key) {
        SearchResponse response = elasticSearchDAO.getPages(key, COUNT_PER_PAGE, id, otherValue, flag, null);
        return elasticSearchDAO.SearchResponseToForumArticleRespFromES(response);
    }

    @Override
    public ForumArticleRespFromES searchMyArticles(Long id, Object otherValue, Integer flag, String key, Long currentUserId) {
        List<QueryBuilder> queryBuilders = new ArrayList<>();
        queryBuilders.add(QueryBuilders.matchQuery("uid", currentUserId));
        SearchResponse response = elasticSearchDAO.getPages(key, COUNT_PER_PAGE, id, otherValue, flag, queryBuilders);
        return elasticSearchDAO.SearchResponseToForumArticleRespFromES(response);
    }

    @Override
    public ForumArticle getArticleById(Long id) {
        return articleMapper.selectById(id);

    }
}
