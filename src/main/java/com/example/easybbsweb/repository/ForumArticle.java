package com.example.easybbsweb.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumArticle {
    @Id
    private String id;
    private String author;
    private String content;
    private Date createTime;
    private Integer visited; // 访问量
}
