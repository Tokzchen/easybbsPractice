package com.example.easybbsweb.config;


import com.example.easybbsweb.domain.entity.ForumArticle;
import org.apache.http.HttpHost;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig
{
    @Bean
    public RestHighLevelClient getRestClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost("123.60.81.108", 9200, "http"));
        return new RestHighLevelClient(builder);
    }

}
