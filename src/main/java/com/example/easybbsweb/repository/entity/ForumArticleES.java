package com.example.easybbsweb.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 *  只存文章本身内容，排除评论区
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ForumArticle")
public class ForumArticleES implements Serializable {
    @Id
    private String id;
    private String email;
    private String title;
    private String content;
    private Integer visited; // 访问量
    private Integer like;
    private String category;
    private Long createTime;

}