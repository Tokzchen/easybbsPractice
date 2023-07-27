package com.example.easybbsweb.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumArticleReq {
    private String id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private String token;
    private Integer visited;
}
