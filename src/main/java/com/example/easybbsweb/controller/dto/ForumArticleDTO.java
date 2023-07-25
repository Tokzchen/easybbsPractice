package com.example.easybbsweb.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumArticleDTO {
    private String id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private String token;
    private Integer visited;
}
