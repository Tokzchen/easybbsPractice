package com.example.easybbsweb.repository;

import com.example.easybbsweb.repository.entity.ForumArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumArticleRepository extends MongoRepository<ForumArticle, String> {

    Page<ForumArticle> findByOrderByCreateTimeDesc(Pageable pageable);
    Page<ForumArticle> findByOrderByCreateTimeAsc(Pageable pageable);

}
