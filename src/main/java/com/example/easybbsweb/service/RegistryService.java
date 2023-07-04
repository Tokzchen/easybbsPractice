package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UserInfo;

public interface RegistryService {
    boolean registerUser(UserInfo userInfo);

    boolean registerUniversity(University university);
}
