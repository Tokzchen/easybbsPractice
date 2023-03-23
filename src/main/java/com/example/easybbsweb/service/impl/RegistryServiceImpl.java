package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.IdSelector;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.entity.UserInfoExample;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.mapper.UserMainMapper;
import com.example.easybbsweb.service.RegistryService;
import com.example.easybbsweb.utils.GenerateIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
@Slf4j
public class RegistryServiceImpl implements RegistryService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserMainMapper userMainMapper;
    public void checkUser(UserInfo userInfo) throws Exception{
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andEmailEqualTo(userInfo.getEmail());
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if(userInfos.size()>0){
                throw new IncorrectInfoException("该邮箱已经注册过");
        }

    }
    @Override
    public boolean registerUser(UserInfo userInfo)  {
        try {
            checkUser(userInfo);
            userInfo.setUserId(GenerateIdUtils.generateIdByEntity(IdSelector.USER));
            log.info("生成用户id{}",userInfo.getUserId());
            //密码md5加密
            userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().trim().getBytes()));
            Integer integer = userInfoMapper.insertSelective(userInfo);
            Integer integer1= userMainMapper.insertSelective(userInfo);
            if(integer>0&&integer1>0){
                log.info("注册成功,新用户id{}",userInfo.getUserId());
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
