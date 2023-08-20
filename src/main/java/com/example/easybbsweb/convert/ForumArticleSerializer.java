package com.example.easybbsweb.convert;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


public class ForumArticleSerializer extends JSONSerializer {
    @Override
    public void write(String text) {
        super.write(text);
    }
}
