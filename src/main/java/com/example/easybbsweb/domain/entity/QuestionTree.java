package com.example.easybbsweb.domain.entity;

import lombok.Data;

import java.util.List;

@Data
public class QuestionTree {
    private QuizNode rootNode;
    private List<QuestionTree> children;
}
