package com.example.easybbsweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.easybbsweb.mapper")
public class EasybbsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasybbsWebApplication.class, args);
    }

}
