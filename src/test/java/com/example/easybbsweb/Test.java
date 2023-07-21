package com.example.easybbsweb;

import com.example.easybbsweb.repository.ForumArticle;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        new Test().html2article("<p>Swagger-ui</p><p>HL</p>");
    }
    public void html2article(String html){
        System.out.println(new Date());
    }
}
