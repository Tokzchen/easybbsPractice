package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.others.LawAidInfoPageUser;
import org.springframework.stereotype.Service;


public interface LawAidService {
    public boolean saveDocumentPath(University university);

    public LawAidInfoPageUser getUserLawAidInfo(Long uniId);


    public void generateIndexOne(Long uniId);

    public void generateIndexTwo(Long uniId);
}
