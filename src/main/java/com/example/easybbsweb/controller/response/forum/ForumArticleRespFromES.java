package com.example.easybbsweb.controller.response.forum;

import com.example.easybbsweb.domain.entity.ForumArticle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumArticleRespFromES {
    List<ForumArticle> articles;
    List<Object> sort;
}
