package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.IdSelector;
import com.example.easybbsweb.domain.entity.*;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.mapper.UniversityMapper;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.mapper.UserMainMapper;
import com.example.easybbsweb.service.ForumArticleService;
import com.example.easybbsweb.service.RegistryService;
import com.example.easybbsweb.utils.GenerateIdUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.IdGenerator;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RegistryServiceImpl implements RegistryService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserMainMapper userMainMapper;

    @Autowired
    private UniversityMapper universityMapper;

    @Resource
    private ForumArticleService forumArticleService;
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
            UserMain userMain = new UserMain();
            userMain.setUserId(userInfo.getUserId());
            userMain.setLastUpdateTime(new Date());
            Integer integer1= userMainMapper.insertSelective(userMain);
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

    @Override
    public boolean registerUniversity(University university) {
        UniversityExample universityExample = new UniversityExample();
        universityExample.createCriteria().andEmailEqualTo(university.getEmail());
        List<University> universities = universityMapper.selectByExample(universityExample);
        if(universities.size()>0){
            throw new BusinessException("该邮箱已被注册");
        }
        //修改完整信息，生成账号id
        Long aLong = GenerateIdUtils.generateIdByEntity(IdSelector.USER);
        university.setUniId(aLong);
        int i = universityMapper.insertSelective(university);
        if(i>0){
            return true;
        }else{
            return false;

        }
    }
}
