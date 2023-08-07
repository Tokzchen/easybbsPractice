package com.example.easybbsweb.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("article_comment_likes")
public class ForumArticleCommentLikes {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long uid;
    private Long cmtId;
    private Date createTime;
    private Date updateTime;

}