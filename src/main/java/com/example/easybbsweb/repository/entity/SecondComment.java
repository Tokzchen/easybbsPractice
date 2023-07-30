package com.example.easybbsweb.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "SecondComment")
public class SecondComment implements Serializable {
    @Id
    private String id;
    private String email;
    private String content ;
    private Long createTime;
    private Integer like = 0;
    private Set<String> userWhoLikes;
    // 这条二级评论是否是对某条二级评论的回复，如果没有则为null
    private String reply;
}
