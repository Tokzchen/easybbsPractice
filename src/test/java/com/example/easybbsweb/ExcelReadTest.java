package com.example.easybbsweb;



import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.example.easybbsweb.domain.entity.Answer;
import com.example.easybbsweb.domain.entity.Question;
import com.example.easybbsweb.listener.AnswerListener;
import com.example.easybbsweb.listener.QuestionListener;
import com.example.easybbsweb.mapper.AnswerMapper;
import com.example.easybbsweb.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

@SpringBootTest
@Slf4j
public class ExcelReadTest {
//    @Autowired
////    ResourceLoader resourceLoader;
//    @Autowired
//    QuestionMapper questionMapper;

    @Autowired
    AnswerMapper answerMapper;

//    @Test
//    void loadExcelTest1() throws IOException {
//        File file = resourceLoader.getResource("classpath:excel/seg1_q_1.xls").getFile();
//        String filepath=file.getPath();
//        EasyExcel.read(filepath, Question.class, new QuestionListener(questionMapper)).sheet().doRead();
//    }
//
//    @Test
//    void loadExcelTest2() throws IOException {
//        File file=resourceLoader.getResource("classpath:excel/seg1_a_1.xls").getFile();
//        String filePath=file.getPath();
//        EasyExcel.read(filePath, Answer.class,new AnswerListener(answerMapper)).sheet().doRead();
//    }


}
