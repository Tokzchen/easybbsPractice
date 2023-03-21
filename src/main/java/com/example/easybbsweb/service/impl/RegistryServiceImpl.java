package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.mapper.UserMainMapper;
import com.example.easybbsweb.service.RegistryService;
import com.example.easybbsweb.utils.GenerateIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class RegistryServiceImpl implements RegistryService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserMainMapper userMainMapper;
    public void checkUser(UserInfo userInfo) throws Exception{
        UserInfo userInfo1 = userInfoMapper.selectByUsername(userInfo.getUsername());
        if(userInfo1!=null){
                throw new IncorrectInfoException("用户名已存在");
        }

    }
    @Override
    public boolean registerUser(UserInfo userInfo)  {
        try {
            checkUser(userInfo);
            userInfo.setUserId(GenerateIdUtils.generateID());
            //密码md5加密
            userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().trim().getBytes()));
            Integer integer = userInfoMapper.insertUser(userInfo);
            Integer integer1= userMainMapper.insertUser(userInfo);
            if(integer>0&&integer1>0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
