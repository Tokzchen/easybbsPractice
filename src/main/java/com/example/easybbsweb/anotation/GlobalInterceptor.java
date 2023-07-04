package com.example.easybbsweb.anotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GlobalInterceptor {
     boolean checkParameters() default false;

     boolean checkIsLogin() default false;

}
