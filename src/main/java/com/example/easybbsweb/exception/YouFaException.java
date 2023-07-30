package com.example.easybbsweb.exception;

import com.example.easybbsweb.common.YouFaExceptionStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class YouFaException extends RuntimeException{
    private int code;
    private  String msg;
    public YouFaException(YouFaExceptionStatusCode statusCode){
        code = statusCode.code;
        msg = statusCode.msg;
    }
}
