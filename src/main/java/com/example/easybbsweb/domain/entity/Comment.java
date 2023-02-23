package com.example.easybbsweb.domain.entity;

import com.example.easybbsweb.domain.others.TopType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer commentId;
    private Integer pCommentId;
    private String articleId;
    private String content;
    private String imgPath;
    private String userId;
    private String nickName;
    private String userIpAddress;
    private String replyUserId;
    private String replyNickName;
    private Integer topType= TopType.NO_TOP;//0-未指定 1-置顶

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;
    private Integer goodCount;
    private Integer status=1;//0-审核未通过，1-审核通过


    //这是为了方便返回数据设置的属性，表示该条评论下面的子评论集合
    private List<Comment> child;


    public Integer getpCommentId(){
        return this.pCommentId;
    }

    public void setpCommentId(Integer pCommentId){
        this.pCommentId=pCommentId;
    }
}
