package com.example.easybbsweb.repository;

import com.example.easybbsweb.anotation.SynElasticSearchDatabase;
import com.example.easybbsweb.common.ElasticSearchOption;
import com.example.easybbsweb.repository.entity.ForumArticle;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ForumArticleRepository {
    @Resource
    MongoTemplate mongoTemplate;

    /**
     * 更新文章 保存文章
     */
    @SynElasticSearchDatabase(operator = ElasticSearchOption.SAVE)
    public ForumArticle save(ForumArticle forumArticle) {
        return mongoTemplate.save(forumArticle);
    }
    public ForumArticle updateComment(ForumArticle forumArticle) {
        return mongoTemplate.save(forumArticle);
    }

    /**
     * 删除文章
     */
    @SynElasticSearchDatabase(operator = ElasticSearchOption.DELETE)
    public void deleteById(String id) {
        mongoTemplate.findAndRemove(Query.query(Criteria.where("id").is(id)), ForumArticle.class);
    }

    public ForumArticle findById(String id) {
        return mongoTemplate.findById(id, ForumArticle.class);
    }

}
