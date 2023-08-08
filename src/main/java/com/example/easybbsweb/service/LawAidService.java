package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.others.LawAidInfoPageUser;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;


public interface LawAidService {
     boolean saveDocumentPath(University university);

     LawAidInfoPageUser getUserLawAidInfo(Long uniId);


     List<University> generateAndRecommendUniversities(Long userId, Point point);
}
