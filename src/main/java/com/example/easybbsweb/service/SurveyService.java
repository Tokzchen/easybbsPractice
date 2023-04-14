package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.TestRecord;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.others.SurveyPair;
import com.example.easybbsweb.domain.others.SurveyResult;

public interface SurveyService {
    SurveyPair startSurvey(UserInfo userInfo);

    SurveyPair continueSurvey(UserInfo userInfo);

    SurveyResult generateSurveyResult(UserInfo userInfo);
}
