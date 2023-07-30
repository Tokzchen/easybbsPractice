package com.example.easybbsweb.service.impl;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.easybbsweb.controller.request.forum.ForumArticleReq;
import com.example.easybbsweb.controller.response.forum.ForumArticleResp;
import com.example.easybbsweb.controller.response.forum.ForumArticleSimple;
import com.example.easybbsweb.repository.entity.ArticleComment;
import com.example.easybbsweb.repository.entity.ForumArticleES;
import com.example.easybbsweb.repository.entity.SecondComment;
import com.example.easybbsweb.repository.entity.ForumArticle;
import com.example.easybbsweb.repository.ForumArticleRepository;
import com.example.easybbsweb.repository.es.ForumArticleElasticSearchDAO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@PropertySource("classpath:/config/application.yml")
public class ForumArticleService {
    @Resource
    ForumArticleRepository forumArticleRepository;
    @Resource
    ForumArticleElasticSearchDAO searchDAO;
    @Resource
    ForumUserService forumUseService;
    @Resource
    ForumCommentService commentService;
    @Resource
    ForumSecondCommentService secondCommentService;
    @Value("${articleConfig.countPerPage}")
    Integer COUNT_PER_PAGE;

    public void saveArticle(ForumArticleReq dto, String email) {
        ForumArticle forumArticle =
                new ForumArticle(UUID.randomUUID().toString(), email, dto.getTitle(), dto.getContent(), new Date().getTime(), dto.getCategory(), 0, 0, dto.getComments(), new HashSet<>());
        System.out.println(dto);
        System.out.println(forumArticle);
        ForumArticle article = forumArticleRepository.save(forumArticle);
        forumUseService.addArticle(email, article.getId());
    }

    /**
     *
     * 更新文章
     */
    public void updateArticle(ForumArticleReq dto) {
        ForumArticle forumArticle = new ForumArticle();
        BeanUtils.copyProperties(dto, forumArticle);
        forumArticleRepository.save(forumArticle);
    }
    public void likeArticle(ForumArticleReq dto,String email){
        forumUseService.likeArticle(email,dto.getId());
        updateArticle(dto);
    }
    public void unlikeArticle(ForumArticleReq dto,String email){
        forumUseService.unlikeArticle(email,dto.getId());
        updateArticle(dto);
    }



    /**
     * 删除文章
     * 所有喜欢这篇文章人，更改likes
     * 遍历删除所有评论
     * 所有喜欢评论的人，更改likes
     */
    public void deleteArticle(String id, String email) {
        ForumArticle forumArticle = forumArticleRepository.findById(id);
        for (String userWhoLike : forumArticle.getUserWhoLikes()) { // 文章点赞
            forumUseService.unlikeArticle(userWhoLike, id);
        }
        for (ArticleComment comment : forumArticle.getComments()) { // 评论点赞
            commentService.ALlNotLikeComment(comment);
            for (SecondComment secondComment : comment.getSecondComments()) {
                secondCommentService.ALlNotLikeSecondComment(secondComment); // 二级评论点赞
            }
        }
        forumUseService.deleteArticle(email, id);
        forumArticleRepository.deleteById(id); // 删除文章
    }




    public ForumArticle findArticleById(String id) {

        return forumArticleRepository.findById(id);
    }

    public ForumArticleResp getPages(String id, String otherValue, Integer flag, String key) {
        SearchResponse<ForumArticleES> pages = searchDAO.getPages(key, COUNT_PER_PAGE, id, otherValue, flag);
        Iterator<Hit<ForumArticleES>> iterator = pages.hits().hits().iterator();
        List<ForumArticleSimple> articles = new ArrayList<>();
        List<String> values = new ArrayList<>();
        while (iterator.hasNext()) {
            Hit<ForumArticleES> next = iterator.next();
            ForumArticleSimple forumArticleSimple = new ForumArticleSimple();
            assert next.source() != null;
            BeanUtils.copyProperties(next.source(), forumArticleSimple);
            articles.add(forumArticleSimple);
            System.out.println(next.source());
            if (!iterator.hasNext()) {
                values.add(next.sort().get(0)._get().toString());
                values.add(next.sort().get(1)._get().toString());
            }
        }

        return new ForumArticleResp(articles, values);
    }


}
