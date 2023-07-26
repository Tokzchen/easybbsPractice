package com.example.easybbsweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfo {
    private boolean flag;
    private String msg;
    private Object data;

    public static ResultInfo OK(Object json) {
        return new ResultInfo(true, "success", json);
    }
    public static ResultInfo OK() {
        return new ResultInfo(true, "success", null);
    }

    public static ResultInfo Fail(){return new ResultInfo(false,"failed",null);}

    public static ResultInfo Fail(Object json){return new ResultInfo(false,"failed",json);}
}
