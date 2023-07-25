package com.example.easybbsweb.repository;

import com.example.easybbsweb.repository.entity.ForumUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumUserRepository extends MongoRepository<ForumUser, String> {
    ForumUser getForumUserByEmail(String email);
}
