package com.example.easybbsweb.aspect;

import com.example.easybbsweb.anotation.SynElasticSearchDatabase;
import com.example.easybbsweb.common.ElasticSearchOption;
import com.example.easybbsweb.common.YouFaExceptionStatusCode;
import com.example.easybbsweb.exception.YouFaException;
import com.example.easybbsweb.repository.entity.ForumArticle;
import com.example.easybbsweb.repository.entity.ForumArticleES;
import com.example.easybbsweb.repository.es.ForumArticleElasticSearchDAO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class DatabaseAspect {
    @Resource
    ForumArticleElasticSearchDAO forumArticleElasticSearchDAO;

    @Pointcut("@annotation(com.example.easybbsweb.anotation.SynElasticSearchDatabase)")
    public void pt() {
    }

    @Before("pt()")
    public void before(JoinPoint joinPoint) throws IOException {
        //获取entity
        Object arg = joinPoint.getArgs()[0];
        // 获取注释value
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SynElasticSearchDatabase annotation = signature.getMethod().getAnnotation(SynElasticSearchDatabase.class);
        String operator = annotation.operator();
        if (Objects.equals(operator, ElasticSearchOption.SAVE)&& arg instanceof ForumArticle) {
            ForumArticleES forumArticleES = new ForumArticleES();
            BeanUtils.copyProperties((ForumArticle) arg,forumArticleES);
            forumArticleElasticSearchDAO.save(forumArticleES);
        }else if (Objects.equals(operator, ElasticSearchOption.DELETE)&& arg instanceof String) {
            forumArticleElasticSearchDAO.deleteById((String)arg);
        }else{
            throw new YouFaException(YouFaExceptionStatusCode.ELASTIC_SYNCHRONIZED_ERROR);
        }
    }
}
