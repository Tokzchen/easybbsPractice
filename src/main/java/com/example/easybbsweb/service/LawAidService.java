package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.others.LawAidInfoPageUser;
import com.example.easybbsweb.domain.others.lawAid.UniversityPair;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;


public interface LawAidService {
     boolean saveDocumentPath(University university);

     LawAidInfoPageUser getUserLawAidInfo(Long uniId);


     List<UniversityPair> generateAndRecommendUniversities(Long userId, Point point,String area);

    boolean userApplyUniLawAid(UserInfo user, University university);
}
