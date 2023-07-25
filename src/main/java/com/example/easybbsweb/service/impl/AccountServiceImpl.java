package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.entity.UserInfoExample;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.exception.SystemException;
import com.example.easybbsweb.mapper.UniversityMapper;
import com.example.easybbsweb.mapper.UserInfoMapper;
import com.example.easybbsweb.mapper.UserMainMapper;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.utils.CheckCodeUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserMainMapper userMainMapper;

    @Autowired
    UniversityMapper universityMapper;
    @Override
    public boolean resetPwd(UserInfo userInfo, HttpServletRequest req) {
        boolean b = false;
        try {
            b = CheckCodeUtils.verifyEmailCodeByRedis(req, userInfo.getEmailCode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(b){
            UserInfo newPwd = new UserInfo();
            //密码加密处理
            newPwd.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().trim().getBytes()));

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
        if(userInfos.size()==0){
            log.info("未注册用户{}",userInfo.getEmail());
            return false;
        }
        UserInfo realUser =  userInfos.get(0);
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
        return userInfo;
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        UserInfoExample exmple = new UserInfoExample();
        exmple.createCriteria().andUserIdEqualTo(userId);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(exmple);
        if(userInfos.size()==0){
            throw new BusinessException("用户尚未注册");
        }

        return userInfos.get(0);
    }

    @Override
    public Integer checkUserIdentity(String userOrUniId) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(Long.parseLong(userOrUniId));
        University university = universityMapper.selectByPrimaryKey(Long.parseLong(userOrUniId));
        if(userInfo==null&&university==null){
            return -1;
        }else{
            return  userInfo!=null?0:1;
        }
    }

    @Override
    public boolean saveUserAvatarPath(UserInfo userInfo) {
        if(!StringUtils.hasText(userInfo.getAvatar())){
            throw new SystemException("路径为空");
        }
        if(userInfo.getUserId()==null){
            throw new SystemException("修改用户不得为空");
        }
        int i = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        return i==1;
    }

    @Override
    public List<UserInfo> getAllUser() {
        //空条件，把所有userInfo查出来
        List<UserInfo> userInfos = userInfoMapper.selectByExample(null);
        return userInfos;
    }


}