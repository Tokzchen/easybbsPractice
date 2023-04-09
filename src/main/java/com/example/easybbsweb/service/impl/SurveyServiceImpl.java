package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.entity.*;
import com.example.easybbsweb.domain.others.SurveyPair;
import com.example.easybbsweb.mapper.AnswerMapper;
import com.example.easybbsweb.mapper.QuestionMapper;
import com.example.easybbsweb.service.SurveyService;
import com.example.easybbsweb.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class SurveyServiceImpl implements SurveyService {
    private String surveyStack=":surveyStack";
    private String surveyCnt=":surveyCnt";
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    AnswerMapper answerMapper;
     /*
     * 规定防止用户的答题长度过长，我们规定这样的算法
     * 弹出的SurveyPair最长只能为10道题，userId:surveyCnt=10
     * 每从Redis弹出一个SurveyPair,返回结果前都需要查看surveyCnt,根据cnt的不同,采取
     * 1.surveyCnt>7 可以return 1,0,-1
     * 2.surveyCnt>5 可以return 1,0
     * 3.surveyCnt<=5且>0 仅可以返回1
     * 4.surveyCnt==0 返回null结束测评
     * */

    @Override
    public SurveyPair startSurvey(UserInfo userInfo) {
        //生成surveyCnt
        RedisUtils.set(userInfo.getUserId()+surveyCnt,10);
        //先清空问题栈
        RedisUtils.stackClear(userInfo.getUserId()+surveyStack);
        Answer answer = new Answer();//ans_id为空
        generateSurveyPair(answer, userInfo);
        SurveyPair surveyPair = (SurveyPair)RedisUtils.stackPop(userInfo.getUserId() + ":surveyStack");
        //因为这里是startSurvey可以直接返回,surveyCnt-1
        RedisUtils.decr(userInfo.getUserId()+surveyCnt,1);
        return surveyPair;

    }

    @Override
    public SurveyPair continueSurvey(UserInfo userInfo) {
        Answer answer = new Answer();
        answer.setAnsId(userInfo.getAnsId());
        generateSurveyPair(answer,userInfo);
        SurveyPair surveyPair = (SurveyPair)RedisUtils.stackPop(userInfo.getUserId() + surveyStack);
        //问题栈中已经清空，可以结束测评了
        if(surveyPair==null){
            log.info("从这结束");
            return null;
        }
        Integer cnt= (Integer)RedisUtils.get(userInfo.getUserId() + surveyCnt);
        if(cnt>7){
            RedisUtils.decr(userInfo.getUserId()+surveyCnt,1);
            return surveyPair;
        }else if(cnt>5&&cnt<=7){
            if(surveyPair.getQuestion().getImportance()==-1){
                while (true){
                    SurveyPair surveyPair1 =(SurveyPair) RedisUtils.stackTop(userInfo.getUserId() + surveyStack);
                    if(surveyPair1.getQuestion().getImportance()==-1){
                        RedisUtils.stackPop(userInfo.getUserId() + surveyStack);
                    }else{
                        return surveyPair1;
                    }
                }
            }else {
                RedisUtils.decr(userInfo.getUserId()+surveyCnt,1);
                return surveyPair;
            }
        }else if(cnt>0&&cnt<=5){
            if(surveyPair.getQuestion().getImportance()==1){
                RedisUtils.decr(userInfo.getUserId()+surveyCnt,1);
                return surveyPair;

            }else{
                while (true){
                    SurveyPair surveyPair1 =(SurveyPair) RedisUtils.stackTop(userInfo.getUserId() +surveyStack);
                    if(surveyPair1.getQuestion().getImportance()==1){
                        return surveyPair1;
                    }else{
                        RedisUtils.stackPop(userInfo.getUserId() + surveyStack);
                    }
                }

            }

        }else{
            //测评结束惹
            log.info("由于cnt归0结束");
            return null;
        }
    }

    /*
   实现思路:我们认定用户在测评模块都会传一个answer过来，我们根据这个answer获取相关的问题
   然后再把问题相关的答案也查出来，得到不定个SurveyPair,将其保存在Redis当中
    */
    protected void generateSurveyPair(Answer answer,UserInfo userInfo){
        QuestionExample questionExample = new QuestionExample();
        if(answer.getAnsId()==null){
            questionExample.createCriteria().andAnsIdIsNull();
        }else{
            questionExample.createCriteria().andAnsIdEqualTo(answer.getAnsId());
        }
        List<Question> questions = questionMapper.selectByExample(questionExample);
        for(int i=questions.size()-1;i>=0;i--){
            //遍历每个问题，查找对应的答案，生成SurveyPair并且压入栈中
            //由于数据特点，这里我们是逆序压入栈中
            Question question=questions.get(i);

            //查找问题的相关答案
            AnswerExample answerExample = new AnswerExample();
            answerExample.createCriteria().andQueIdEqualTo(question.getQueId());
            List<Answer> answers = answerMapper.selectByExample(answerExample);
            //生成SurveyPair并压入栈中
            SurveyPair surveyPair = new SurveyPair();
            surveyPair.setQuestion(question);
            surveyPair.setAnswerList(answers);
            boolean b = RedisUtils.stackPush(userInfo.getUserId() + surveyStack, surveyPair);
            if(!b){
                log.error("尝试向用户问题栈中压入问题发生错误!用户id:{}",userInfo.getUserId());
            }
        }

    }

}
