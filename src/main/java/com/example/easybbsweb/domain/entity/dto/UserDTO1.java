package com.example.easybbsweb.domain.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO1 {
    private Long userId;

    private String username;

    private String gender;

    private Date birthday;

    private String email;

    private String password;

    private String phone;

    private String province;

    private String city;

    private Date lastLoginTime;

    private String lastLoginIp;

    private String issue;

    private Date lastUpdateTime;

    private String emailCode;

    private String checkCode;

    private String avatar;

    private Integer ansId;

    private Integer id;

    private Long uniId;

    private Long lawyerId;

    private Date createTime;

    private String area;
}
