package com.example.easybbsweb.domain.entity;

import lombok.Data;

@Data
public class QuizNode {
    private Integer id;
    private Integer parentId;
    private String path;
    private String nodePath;
    private String content;
    private String advice;
    private Integer rate;
    private Integer deleted;//0-未删除，1-已删除
}
