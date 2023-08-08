package com.example.easybbsweb.domain.entity;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class ForumArticle {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long uid;
    private String title;
    private String content;
    private String category;
    private Integer visited;
    @TableField("`like`")
    private Integer like;
    private Integer good;
    private Integer top;
    private Date createTime;
    private Date updateTime;

}
