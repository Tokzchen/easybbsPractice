package com.example.easybbsweb.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LikeRecord {
    private Integer opId;
    private Integer opType;//0-文章的习惯记录 1-评论的喜欢记录
    private String objectId;
    private String userId;
    private Date createTime;
    private String authorUserId;
}
