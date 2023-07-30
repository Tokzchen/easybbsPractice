package com.example.easybbsweb.controller.response;

import com.example.easybbsweb.common.YouFaExceptionStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.ptg.Ptg;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfo {
    private boolean flag;
    private String msg;
    private Object data;

    public static ResultInfo Success(Object json) {
        return new ResultInfo(true, "success", json);
    }
    public static ResultInfo Success() {
        return new ResultInfo(true, "success", null);
    }
    public static ResultInfo Fail(String msg){
        return new ResultInfo(false,msg,null);
    }
    public static ResultInfo Fail(YouFaExceptionStatusCode code){
        return new ResultInfo(false,code.msg,null);
    }
    public static ResultInfo Fail(){return new ResultInfo(false,"failed",null);}

}
