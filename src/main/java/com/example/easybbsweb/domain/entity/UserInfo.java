package com.example.easybbsweb.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    //user_main部分
    private Integer id;
    private String userId;
    private String password;
    private Integer aidId;
    private Integer lawyerId;
    private Date createTime;
    private String emailCode;
    private String checkCode;
    private Date lastUpdateTime;//共有部分

    //user_info部分
    private String username;
    private String gender;
    private Date birthday;
    private String email;
    private String phone;
    private String province;
    private String city;
    private String issue;

    public void removeSentiveInfo() {
        password="";
        checkCode="";
        emailCode="";
    }
}
