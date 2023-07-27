package com.example.easybbsweb.repository.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ForumArticle")
public class ForumArticle implements Serializable {
    @Id
    private String id;
    private String email;
    private String title;
    private String content;
    @JsonProperty("create_time")
    private Long createTime;
    private Integer visited; // 访问量
    private Integer agree;
}
