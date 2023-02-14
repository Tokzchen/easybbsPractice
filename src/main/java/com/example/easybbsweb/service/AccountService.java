package com.example.easybbsweb.service;


import com.example.easybbsweb.domain.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

public interface AccountService {
    boolean resetPwd(UserInfo userInfo, HttpServletRequest req);

    boolean userLogin(UserInfo userInfo);
}
