package com.example.easybbsweb.service;


import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AccountService {
    boolean resetPwd(UserInfo userInfo, HttpServletRequest req);

    boolean userLogin(UserInfo userInfo);
    
    UserInfo getUserInfoByEmail(String email);

    UserInfo getUserInfoByUserId(Long userId);

    Integer checkUserIdentity(String userOrUniId);

    boolean saveUserAvatarPath(UserInfo userInfo);

    List<UserInfo> getAllUser();

}
