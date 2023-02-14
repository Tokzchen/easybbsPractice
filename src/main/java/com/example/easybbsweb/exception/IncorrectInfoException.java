package com.example.easybbsweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
    public class IncorrectInfoException extends  BusinessException{

    public IncorrectInfoException(String msg){
        super(msg);
    }
}
