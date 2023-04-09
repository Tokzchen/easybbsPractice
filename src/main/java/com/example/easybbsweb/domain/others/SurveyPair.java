package com.example.easybbsweb.domain.others;

import com.example.easybbsweb.domain.entity.Answer;
import com.example.easybbsweb.domain.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
该类主要用于测评模块每次返回给前端的结果集
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyPair {
    private Question question;
    private List<Answer> answerList;
}
