package com.example.easybbsweb.controller.response.forum;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.easybbsweb.domain.entity.ForumArticleSecondComment;
import com.example.easybbsweb.domain.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.elasticsearch.client.license.LicensesStatus;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentResp {
    private Long id;
    private UserInfo user;
    private Long articleId;
    private String content;
    private Integer like;
    private Date createTime;
    private Date updateTime;
    private List<SecondCommentResp> secondComments;
}
