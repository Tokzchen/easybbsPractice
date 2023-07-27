package com.example.easybbsweb.controller;

import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.Answer;
import com.example.easybbsweb.domain.entity.TestRecord;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.others.SurveyPair;
import com.example.easybbsweb.domain.others.SurveyResult;
import com.example.easybbsweb.service.SurveyService;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/survey")
@Tag(name="证据调查模块业务的接口")
public class SurveyController {
    @Autowired
    SurveyService surveyService;
    @GetMapping("/start")
    @Operation(summary = "开始测评的接口")
    public ResultInfo startSurvey(@RequestHeader("token")String token){
        String currentUserId = TokenUtil.getCurrentUserOrUniId(token);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Long.parseLong(currentUserId));
        SurveyPair surveyPair = surveyService.startSurvey(userInfo);
        return new ResultInfo(true,"响应成功!",surveyPair);
    }
    @PostMapping("/continue")
    @Operation(summary = "继续测评的接口",description ="至少需要提供userId,ansId" )
    public ResultInfo continueSurvey(@RequestHeader("token") String token,@RequestBody UserInfo userInfo){
        String currentUserId = TokenUtil.getCurrentUserOrUniId(token);
        userInfo.setUserId(Long.parseLong(currentUserId));
        SurveyPair surveyPair = surveyService.continueSurvey(userInfo);
        if(surveyPair!=null){
            return new ResultInfo(true,"响应成功!",surveyPair);
        }else{
            return new ResultInfo(false,"测评已经结束，请访问生成测评记录",null);

        }
    }

    @GetMapping("/result")
    @Operation(summary = "生成测评内容的接口")
    public ResultInfo getSurveyRecord(@RequestHeader("token") String token){
        String currentUserId=TokenUtil.getCurrentUserOrUniId(token);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Long.parseLong(currentUserId));
        SurveyResult result= surveyService.generateSurveyResult(userInfo);
        return new ResultInfo(true,"生成报告成功",result);
    }

    @GetMapping("/areas")
    @Operation(summary = "获得求助领域")
    public ResultInfo getSurveyArea(){
        List<Answer> surveyArea = surveyService.getSurveyArea();
        return ResultInfo.OK(surveyArea);
    }

}
