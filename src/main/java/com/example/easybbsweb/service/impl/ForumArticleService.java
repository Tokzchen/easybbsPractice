package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.controller.dto.ForumArticleDTO;
import com.example.easybbsweb.repository.entity.ForumArticle;
import com.example.easybbsweb.repository.ForumArticleRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Pageable;
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
    ForumUserService forumUseService;
    @Value("${articleConfig.countPerPage}")
    Integer COUNT_PER_PAGE;
    public void saveArticle(ForumArticleDTO dto, String email){
        ForumArticle forumArticle = new ForumArticle(UUID.randomUUID().toString(), email, dto.getTitle(), dto.getContent(), new Date(), null);
        ForumArticle article = forumArticleRepository.save(forumArticle);
        forumUseService.addArticle(email,article.getId());
    }
    public void updateArticle(ForumArticleDTO dto, String email){
        ForumArticle forumArticle = forumArticleRepository.findById(dto.getId()).get();
        BeanUtils.copyProperties(dto,forumArticle); // id不变
        forumArticleRepository.save(forumArticle);

    }
    public void deleteArticle(String id,String email){
        forumArticleRepository.deleteById(id);
        forumUseService.deleteArticle(email,id);
    }
    /**
     *
     * @return 10 篇文章
     */
    public List<ForumArticle> getPage(Integer page){
        Pageable pageable = Pageable.ofSize(COUNT_PER_PAGE).withPage(page);
        return forumArticleRepository.findByOrderByCreateTimeAsc(pageable).toList();
    }


    public List<ForumArticle> getNewPage(Integer page) {
        Pageable pageable = Pageable.ofSize(COUNT_PER_PAGE).withPage(page);
        return forumArticleRepository.findByOrderByCreateTimeDesc(pageable).toList();
    }
}
