package com.example.easybbsweb.controller.request.forum;

import com.example.easybbsweb.domain.entity.ForumArticleComment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumArticleReq {
    private String id;
    private String email;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private String category;
    private Integer visited;
    private Integer like;
    private Long createTime;
    private List<ForumArticleComment> comments;
    private Set<String> userWhoLikes;
}
