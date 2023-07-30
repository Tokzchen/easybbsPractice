package com.example.easybbsweb.controller.response.forum;

import co.elastic.clients.elasticsearch._types.FieldValue;
import com.example.easybbsweb.repository.entity.ForumArticle;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumArticleResp {
    List<ForumArticleSimple> articles;
    List<String> sort;
}
