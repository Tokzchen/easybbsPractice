package com.example.easybbsweb.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumArticleResp {
    private String id;
    private String email;
    private String title;
    private String content;
    private Long createTime;
    private Integer visited; // 访问量
    private Integer agree;
}
