package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.controller.dto.ForumArticleDTO;
import com.example.easybbsweb.repository.ForumArticle;
import com.example.easybbsweb.repository.ForumArticleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class ForumArticleService {
    ForumArticleRepository forumArticleRepository;
    MongoOperations mongoOperations;
    public ForumArticle html2article(ForumArticleDTO forumArticleDTO){
        int index = forumArticleDTO.getContent().indexOf('<', 1);
        String title = forumArticleDTO.getContent().substring(0,index).split(">")[1];
        return new ForumArticle(UUID.randomUUID().toString(),title,forumArticleDTO.getContent(),new Date(), forumArticleDTO.getVisited());
    }
    public void save(ForumArticle forumArticle){
        forumArticleRepository.save(forumArticle);
    }
    public void delete(String id){
        forumArticleRepository.deleteById(id);
    }
    /**
     *
     * @return 10 篇文章
     */
    public List<ForumArticle> getPage(){
        Pageable pageable = Pageable.ofSize(10);
        return forumArticleRepository.findAll(pageable).toList();
    }





}
