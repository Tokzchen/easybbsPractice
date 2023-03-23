package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.entity.UserInfoExample;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.mapper.UserMainMapper;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.utils.CheckCodeUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserMainMapper userMainMapper;
    @Override
    public boolean resetPwd(UserInfo userInfo, HttpServletRequest req) {
        String sessionCode = (String) req.getSession().getAttribute("emailCode");
        String emailCode = userInfo.getEmailCode();
        boolean b = CheckCodeUtils.verifyEmailCode(sessionCode, emailCode);
        if(b){
            UserInfo newPwd = new UserInfo();
            newPwd.setPassword(userInfo.getPassword());
            //密码加密处理
            userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().trim().getBytes()));
            UserInfoExample userInfoExample = new UserInfoExample();
            userInfoExample.createCriteria().andEmailEqualTo(userInfo.getEmail());
            int i = userInfoMapper.updateByExampleSelective(newPwd, userInfoExample);
            if(i>0){
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
        UserInfoExample exmple = new UserInfoExample();
        exmple.createCriteria().andEmailEqualTo(userInfo.getEmail());
        List<UserInfo> userInfos = userInfoMapper.selectByExample(exmple);
        UserInfo realUser =  userInfos.get(0);
        if(realUser==null){
            return false;
        }
        userInfo.setUserId(realUser.getUserId());
        return realUser.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex( userInfo.getPassword().trim()
                        .getBytes(StandardCharsets.UTF_8)));
    }


    public UserInfo getUserInfoByEmail(String email){
        UserInfoExample exmple = new UserInfoExample();
        exmple.createCriteria().andEmailEqualTo(email);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(exmple);
        UserInfo userInfo =  userInfos.get(0);
        userInfo.removeSensitiveInfo();
        return userInfo;
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        UserInfoExample exmple = new UserInfoExample();
        exmple.createCriteria().andUserIdEqualTo(userId);
        UserInfo userInfo = userInfoMapper.selectByExample(exmple).get(0);

        return userInfo;
    }


}
