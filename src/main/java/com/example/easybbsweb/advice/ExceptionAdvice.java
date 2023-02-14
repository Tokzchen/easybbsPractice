package com.example.easybbsweb.advice;

import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.SystemException;
import com.example.easybbsweb.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {
    public static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultUtil handleException(RuntimeException e) {
        logger.error("服务异常：", e);
        //return ResultUtil.fail(-2, e.getMessage());
        return ResultUtil.fail(-2, e.toString());
    }

    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public ResultUtil handleException(Exception e) {
        logger.error("系统异常：", e);
        return ResultUtil.fail(-2, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResultUtil doBusinessException(Exception e) {
        logger.error("业务异常：", e);
        return ResultUtil.fail(-2, e.getMessage());
    }

}
