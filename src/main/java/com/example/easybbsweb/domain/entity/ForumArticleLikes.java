package com.example.easybbsweb.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("article_likes")
public class ForumArticleLikes {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long uid;
    private Long articleId;
    private Date createTime;
    private Date updateTime;

}