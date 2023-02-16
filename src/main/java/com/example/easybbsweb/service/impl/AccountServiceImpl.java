package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.utils.CheckCodeUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

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
            //密码加密处理
            userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().trim().getBytes()));
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

    @Override
    public boolean userLogin(UserInfo userInfo) {
        UserInfo realUser = userInfoMapper.selectUserByEmail(userInfo.getEmail());
        if(realUser==null){
            return false;
        }
        return realUser.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex( userInfo.getPassword().trim()
                        .getBytes(StandardCharsets.UTF_8)));
    }


    public UserInfo getUserInfoByEmail(String email){
        UserInfo userInfo = userInfoMapper.selectUserByEmail(email);
        userInfo.removeSentiveInfo();
        return userInfo;
    }
}
