package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UniversityExample;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.SystemException;
import com.example.easybbsweb.mapper.UniversityMapper;
import com.example.easybbsweb.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    UniversityMapper universityMapper;

    @Override
    public boolean UniversityLogin(University university) {
        UniversityExample universityExample = new UniversityExample();
        universityExample.createCriteria().andEmailEqualTo(university.getEmail());
        List<University> universities = universityMapper.selectByExample(universityExample);
        if(universities.size()==0){
            throw new BusinessException("该用户尚未注册");
        }
        University realUser=universities.get(0);
        university.setUniId(realUser.getUniId());
        return realUser.getPwd().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex( university.getPwd().trim()
                        .getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public boolean saveUniversityAvatarPath(University university) {
        UniversityExample universityExample = new UniversityExample();
        universityExample.createCriteria().andUniIdEqualTo(university.getUniId());
        //仅保存的是avatar的路径
        if(!StringUtils.hasText(university.getAvatar())){
            throw new SystemException("缺少路径参数");
        }
        University university1 = new University();
        university1.setAvatar(university.getAvatar());
        int i = universityMapper.updateByExampleSelective(university1, universityExample);
        return i==1;
    }

    @Override
    public String getUniversityAvatarPath(University university) {
        UniversityExample universityExample = new UniversityExample();
        universityExample.createCriteria().andUniIdEqualTo(university.getUniId());
        List<University> universities = universityMapper.selectByExample(universityExample);
        return universities.get(0).getAvatar();
    }
}
