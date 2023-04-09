package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.IdSelector;
import com.example.easybbsweb.domain.entity.*;
import com.example.easybbsweb.domain.others.SurveyPair;
import com.example.easybbsweb.mapper.AnswerMapper;
import com.example.easybbsweb.mapper.QuestionMapper;
import com.example.easybbsweb.mapper.TestRecordMapper;
import com.example.easybbsweb.service.SurveyService;
import com.example.easybbsweb.utils.GenerateIdUtils;
import com.example.easybbsweb.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class SurveyServiceImpl implements SurveyService {
    private String surveyStack=":surveyStack";
    private String surveyCnt=":surveyCnt";

    private String surveyRecord=":surveyRecord";
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    AnswerMapper answerMapper;

    @Autowired
    TestRecordMapper testRecordMapper;
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
        //先清空问题栈,与测评结果队列
        RedisUtils.stackClear(userInfo.getUserId()+surveyStack);
        RedisUtils.stackClear(userInfo.getUserId()+surveyRecord);
        Answer answer = new Answer();//首次ans_id为空
        generateSurveyPair(answer, userInfo);
        SurveyPair surveyPair = (SurveyPair)RedisUtils.stackPop(userInfo.getUserId() + ":surveyStack");
        //因为这里是startSurvey可以直接返回,surveyCnt-1
        RedisUtils.decr(userInfo.getUserId()+surveyCnt,1);
        //将问题压入结果队列当中
        RedisUtils.queuePush(userInfo.getUserId()+surveyRecord,surveyPair.getQuestion());
        return surveyPair;

    }

    @Override
    public SurveyPair continueSurvey(UserInfo userInfo) {
        Answer answer = answerMapper.selectByPrimaryKey(userInfo.getAnsId());
        answer.setAnsId(userInfo.getAnsId());
        //根据问题编号查找问题实体，将返回的答案实体压入到结果队列当中
        RedisUtils.queuePush(userInfo.getUserId()+surveyRecord,answer);
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
            RedisUtils.queuePush(userInfo.getUserId()+surveyRecord,surveyPair.getQuestion());
            return surveyPair;
        }else if(cnt>5&&cnt<=7){
            if(surveyPair.getQuestion().getImportance()==-1){
                while (true){
                    SurveyPair surveyPair1 =(SurveyPair) RedisUtils.stackTop(userInfo.getUserId() + surveyStack);
                    if(surveyPair1.getQuestion().getImportance()==-1){
                        RedisUtils.stackPop(userInfo.getUserId() + surveyStack);
                    }else{
                        RedisUtils.queuePush(userInfo.getUserId()+surveyRecord,surveyPair1.getQuestion());
                        return surveyPair1;
                    }
                }
            }else {
                RedisUtils.decr(userInfo.getUserId()+surveyCnt,1);
                RedisUtils.queuePush(userInfo.getUserId()+surveyRecord,surveyPair.getQuestion());
                return surveyPair;
            }
        }else if(cnt>0&&cnt<=5){
            if(surveyPair.getQuestion().getImportance()==1){
                RedisUtils.decr(userInfo.getUserId()+surveyCnt,1);
                RedisUtils.queuePush(userInfo.getUserId()+surveyRecord,surveyPair.getQuestion());
                return surveyPair;

            }else{
                while (true){
                    SurveyPair surveyPair1 =(SurveyPair) RedisUtils.stackTop(userInfo.getUserId() +surveyStack);
                    if(surveyPair1.getQuestion().getImportance()==1){
                        RedisUtils.queuePush(userInfo.getUserId()+surveyRecord,surveyPair1.getQuestion());
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

    @Override
    public TestRecord generateSurveyResult(UserInfo userInfo) {
        //从结果队列中得到测评结果
        StringBuilder tracePath=new StringBuilder();
        StringBuilder resultContent=new StringBuilder();
        resultContent.append("以下是给您的建议:"+"\n");
        TestRecord record = new TestRecord();
        //设置answer effect的初始值
        record.setPosCount(1);
        record.setMedCount(0);
        record.setNegCount(1);
        List<Object> objects = RedisUtils.lGet(userInfo.getUserId() + surveyRecord, 0, -1);
        while (RedisUtils.lGetListSize(userInfo.getUserId()+surveyRecord)>0){
           Question q= (Question) RedisUtils.queuePop(userInfo.getUserId() + surveyRecord);
            Answer a= (Answer) RedisUtils.queuePop(userInfo.getUserId() + surveyRecord);
            tracePath.append(q.getQueId()+".");
            tracePath.append(a.getAnsId()+".");
            if(a.getEffect()==-1){
                record.setNegCount(record.getNegCount()+1);
            }else if(a.getEffect()==0){
                record.setMedCount(record.getMedCount()+1);
            }else if(a.getEffect()==1){
                record.setPosCount(record.getPosCount()+1);
            }
            if(q.getAppend()!=null&&!q.getAppend().equals("")){
                resultContent.append(q.getAppend()+"\n");
            }
            if(a.getAppend()!=null&&!a.getAppend().equals("")){
                resultContent.append(a.getAppend()+"\n");
            }
        }
        if(record.getPosCount()==0){
            resultContent.append("根据测评结果，您的情况比较复杂。"+"\n");
        }else {
            double potion=record.getNegCount()/ record.getPosCount();
            if(potion>=1.5){
                resultContent.append("根据测评结果，您的情况比较复杂。"+"\n");
            }else{
                resultContent.append("根据测评结果，您的情况比较乐观。"+"\n");
            }
        }
        resultContent.append("此外，您还可以通过在优法社区发帖寻求其他求助人的帮助。");
        record.setUserId(userInfo.getUserId());
        record.setReportContent(resultContent.toString());
        record .setTracePath(tracePath.toString());
        record .setCreateTime(new Date());
        int i = testRecordMapper.insertSelective(record);
        if(i>0){
            log.info("用户{}生成测试报告成功",userInfo.getUserId());
        }else{
            log.warn("用户{}生成测试报告失败",userInfo.getUserId());
        }
        //前端不需要这么多的细节，只返回带有result的record即可
        TestRecord returnRecord = new TestRecord();
        returnRecord.setReportContent(record.getReportContent());
        return returnRecord;
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
