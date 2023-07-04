package com.example.easybbsweb.anotation;

import com.example.easybbsweb.utils.VerifyRegexEnum;

import java.lang.annotation.*;
//可以放在参数上界面
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VerifyParam {
    boolean required() default false;
    int min() default -1;
    int max() default -1;
    //正则,默认不校验
    VerifyRegexEnum regex() default VerifyRegexEnum.NO;
}
