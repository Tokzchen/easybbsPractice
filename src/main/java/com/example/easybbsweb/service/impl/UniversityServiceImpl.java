package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UniversityExample;
import com.example.easybbsweb.exception.SystemException;
import com.example.easybbsweb.mapper.UniversityMapper;
import com.example.easybbsweb.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    UniversityMapper universityMapper;
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
}
