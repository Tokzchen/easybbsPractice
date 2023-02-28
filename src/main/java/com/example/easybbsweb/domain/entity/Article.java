package com.example.easybbsweb.domain.entity;


import com.example.easybbsweb.domain.others.TopType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {


    private String articleId;
    private Integer pBoardId;
    private String pBoardName;
    private String userId;
    private String nickName;
    private String userIpAddress;
    private String title;
    private String cover;
    private String content;
    private String markDownContent;
    private String summary;
    private String editorType;//0-富文本编辑器，1-markDown编辑器


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;
    private Integer readCount;
    private Integer goodCount;
    private Integer commentCount;
    private Integer topType= TopType.NO_TOP;
    private Integer attachmentType;
    private Integer status=1;

    //这些是前端可以给出的参数，数据库表不一定有
    private Integer orderType;
    private Integer pageNo;
    private String userIdClickLike;

    public String getpBoardName(){
        return pBoardName;
    }

    public Integer getpBoardId(){
        return pBoardId;
    }

    public void setpBoardId(Integer id){
        this.pBoardId=id;
    }


    public void setpBoardName(String name){
        this.pBoardName=name;
    }

    public void loseWeight(){
        this.content=null;
    }


}
