package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.others.lawAid.UniversityPair;

import java.util.List;

public interface UniversityService {
    boolean UniversityLogin(University university);
    boolean saveUniversityAvatarPath(University university);
    String getUniversityAvatarPath(University university);

    University getUniversityInfoByEmail(University university);

    University getUniInfoById(University university);

    University getUniversityInfoByUniId(University university);

    List<University> getUniversityInfoByUniIdList(List<UniversityPair> uniIdList);
}
