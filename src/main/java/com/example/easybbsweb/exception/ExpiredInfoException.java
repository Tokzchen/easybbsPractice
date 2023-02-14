package com.example.easybbsweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ExpiredInfoException extends BusinessException {
    public ExpiredInfoException(String msg){
        super(msg);
    }
}
