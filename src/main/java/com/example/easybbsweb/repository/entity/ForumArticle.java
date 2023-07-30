package com.example.easybbsweb.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
//    @JsonProperty("create_time")
    private Long createTime;
    private String category;
    private Integer visited; // 访问量
    private Integer like;
    private List<ArticleComment> comments;
    // 赞同这篇文章的人
    private Set<String> userWhoLikes;
}
