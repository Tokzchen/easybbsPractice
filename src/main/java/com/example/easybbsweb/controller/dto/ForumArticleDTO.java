package com.example.easybbsweb.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumArticleDTO {
    @NotNull
    String content;
    @NotNull
    String author;
    Integer visited;
}
