package com.example.easybbsweb.controller.response.forum;

import com.example.easybbsweb.domain.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirstForumArticleResp {
    private Long id;
    private UserInfo user;
    private String title;
    private String content;
    private String category;
    private Integer visited;
    private Integer like;
    private Integer good;
    private Integer top;
    private Date createTime;
    private Date updateTime;
    private List<CommentResp> comments;
}
