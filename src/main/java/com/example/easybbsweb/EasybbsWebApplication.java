package com.example.easybbsweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@MapperScan("com.example.easybbsweb.mapper")
@EnableElasticsearchRepositories("com.example.easybbsweb.repository")
public class EasybbsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasybbsWebApplication.class, args);
    }

}
