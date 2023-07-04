package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UniversityExample;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.mapper.UniversityMapper;
import com.example.easybbsweb.service.LawAidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LawAidServiceImpl implements LawAidService {

    @Autowired
    UniversityMapper universityMapper;

    @Override
    public boolean saveDocumentPath(University university) {
        //首先对university进行参数校验
        if(university.getFile()==null||university.getFile().equals("")){
            throw new BusinessException("存储路径不得为空");
        }
        if(!StringUtils.hasText(university.getUniId())){
            throw new BusinessException("存储对象暂时未登记");
        }

        //重新生成一个对象，将其他的数据置空
        University insertData = new University();
        insertData.setUniId(university.getUniId());
        insertData.setFile(university.getFile());

        UniversityExample universityExample = new UniversityExample();
        universityExample.createCriteria().andUniIdEqualTo(insertData.getUniId());
        int i = universityMapper.updateByExampleSelective( insertData,universityExample);

        return i>0;

    }
}
