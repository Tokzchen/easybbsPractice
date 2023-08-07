package com.example.easybbsweb.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class ForumArticleSecondComment {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long uid;
    private Long cmtId;
    private String content;
    @TableField("`like`")
    private Integer like;
    private Long replyUid;
    private Date createTime;
    private Date updateTime;

}
