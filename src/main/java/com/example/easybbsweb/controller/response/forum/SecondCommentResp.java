package com.example.easybbsweb.controller.response.forum;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.easybbsweb.domain.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SecondCommentResp {
    private Long id;
    private UserInfo user;
    private Long cmtId;
    private String content;
    private Integer like;
    private UserInfo replyUser;
    private Date createTime;
    private Date updateTime;

}