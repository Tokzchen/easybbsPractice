package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.service.RegistryService;
import com.example.easybbsweb.utils.GenerateIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistryServiceImpl implements RegistryService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    public void checkUser(UserInfo userInfo) throws Exception{
        UserInfo userInfo1 = userInfoMapper.selectByUsername(userInfo.getNickName());
        if(userInfo1!=null){
                throw new IncorrectInfoException("用户名已存在");
        }

    }
    @Override
    public boolean registerUser(UserInfo userInfo)  {
        try {
            checkUser(userInfo);
            userInfo.setUserId(GenerateIdUtils.generateID());
            Integer integer = userInfoMapper.insertUser(userInfo);
            if(integer>0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
