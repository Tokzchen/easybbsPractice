package com.example.easybbsweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumArticleRepository extends MongoRepository<ForumArticle, String> {
}
