package com.example.easybbsweb.repository;

import com.example.easybbsweb.repository.entity.ForumUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
//可以使用@Document进行注解进行collection(表)名的指定，不然的话默认使用实体类的名称作为collection
public interface ForumUserRepository extends MongoRepository<ForumUser, String> {
    ForumUser getForumUserByEmail(String email);
}
