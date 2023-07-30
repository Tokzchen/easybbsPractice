package com.example.easybbsweb.controller.response.forum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ForumArticle")
public class ForumArticleSimple {
    @Id
    private String id;
    private String email;
    private String title;
    private Long createTime;
    private Integer visited; // 访问量
    private Integer agree;
}
