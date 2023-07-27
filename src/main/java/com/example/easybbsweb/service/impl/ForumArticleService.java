package com.example.easybbsweb.service.impl;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.easybbsweb.controller.request.ForumArticleReq;
import com.example.easybbsweb.controller.response.ForumArticleResp;
import com.example.easybbsweb.repository.entity.ForumArticle;
import com.example.easybbsweb.repository.ForumArticleRepository;
import com.example.easybbsweb.repository.es.ForumArticleElasticSearchDAO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@PropertySource("classpath:/config/application.yml")
public class ForumArticleService {
    @Resource
    ForumArticleRepository forumArticleRepository;
    @Resource
    ForumArticleElasticSearchDAO searchDAO;
    @Resource
    ForumUserService forumUseService;
    @Value("${articleConfig.countPerPage}")
    Integer COUNT_PER_PAGE;
    public void saveArticle(ForumArticleReq dto, String email){
        ForumArticle forumArticle = new ForumArticle(UUID.randomUUID().toString(), email, dto.getTitle(), dto.getContent(), new Date().getTime(), 0,0);
        ForumArticle article = forumArticleRepository.save(forumArticle);
        forumUseService.addArticle(email,article.getId());
    }
    public void updateArticle(ForumArticleReq dto, String email){
        ForumArticle forumArticle = forumArticleRepository.findById(dto.getId()).get();
        BeanUtils.copyProperties(dto,forumArticle); // id不变
        forumArticleRepository.save(forumArticle);

    }
    public void deleteArticle(String id,String email){
        forumArticleRepository.deleteById(id);
        forumUseService.deleteArticle(email,id);
    }
    public List<ForumArticleResp> getPages(String id, Object otherValue, Integer flag, String key){
        SearchResponse<ForumArticle> pages = searchDAO.getPages(key, COUNT_PER_PAGE, id, otherValue, flag);

        return null;
    }
}
