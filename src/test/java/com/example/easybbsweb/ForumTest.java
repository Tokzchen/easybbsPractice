package com.example.easybbsweb;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.easybbsweb.repository.ForumArticleRepository;
import com.example.easybbsweb.repository.es.ForumArticleElasticSearchDAO;
import com.example.easybbsweb.repository.entity.ForumArticle;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ForumTest {
    @Resource
    ElasticsearchTemplate elasticsearchTemplate;
    @Resource
    ForumArticleElasticSearchDAO bookRepository;
    @Resource
    MongoTemplate mongoTemplate;
    @Resource
    ForumArticleRepository forumArticleRepository;

    @Test
    void func1() throws IOException {
        ForumArticle forumArticle = new ForumArticle("1","@","ouo","您吃了嘛",new Date().getTime(),0,1);
        bookRepository.show("d");
    }

    @Test
    void func2() throws IOException {
        SearchResponse<ForumArticle> pages = bookRepository.getPages("fresh", 5, null, null, 3);
//        System.out.println(pages.hits().);
        pages.hits().hits().forEach(g->{
            System.out.println(g);
        });
    }
}
