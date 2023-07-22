package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.University;

public interface UniversityService {
    boolean saveUniversityAvatarPath(University university);
    String getUniversityAvatarPath(University university);
}
