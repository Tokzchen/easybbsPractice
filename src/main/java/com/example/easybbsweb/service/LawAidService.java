package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.University;
import org.springframework.stereotype.Service;


public interface LawAidService {
    public boolean saveDocumentPath(University university);
}
