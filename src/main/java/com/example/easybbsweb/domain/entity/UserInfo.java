package com.example.easybbsweb.domain.entity;

import com.example.easybbsweb.domain.others.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//这个包主要是封装数据库相关的实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String userId;
    private String nickName;
    private String email;
    private String password;
    private Integer sex;//male-1 female -0
    private String personDescription;
    private Date joinTime;
    private Date lastLoginTime;
    private String lastLoginIp;
    private String lastLoginIpAddress;
    private Integer totalIntegral;
    private Integer currentIntegral;
    private Integer status;//0-禁用 1-正常
    private String emailCode;
    private String checkCode;


    //一下字段为配合前端的字段，数据库没有
    private List<LikeRecord> likeRecords;



    public boolean removeSentiveInfo(){
        password="";
        emailCode="";
        checkCode="";
        return true;
    }

}
