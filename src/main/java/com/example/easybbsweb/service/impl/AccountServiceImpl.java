package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.utils.CheckCodeUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public boolean resetPwd(UserInfo userInfo, HttpServletRequest req) {
        String sessionCode = (String) req.getSession().getAttribute("emailCode");
        String emailCode = userInfo.getEmailCode();
        boolean b = CheckCodeUtils.verifyEmailCode(sessionCode, emailCode);
        if(b){
            Integer integer = userInfoMapper.updateUserByEmail(userInfo);
            if(integer>0){
                return true;
            }else{
                return false;
            }
        }else{
            throw new IncorrectInfoException("验证码错误");
        }
    }
}
