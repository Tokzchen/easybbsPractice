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
@Document(collection = "ArticleComment")
public class ArticleComment implements Serializable {
    @Id
    private String id;
    private String email;
    private String content ;
//    @JsonProperty("create_time")
    private Long createTime;
    private Integer like = 0;
    private Set<String> userWhoLikes;
    private List<SecondComment> secondComments;
}
