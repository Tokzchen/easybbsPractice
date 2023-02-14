package com.example.easybbsweb.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

public class ResultUtil implements Serializable {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;//0成功 -1失败 -2异常
    //like12 add,20210902
    private String result;//success fail
    // 响应消息
    private String msg;
    // 响应中的数据
    private Object data;
    // 响应中的token
    private String token;

    //构造函数
    //like12 modified,20210901,只返msg不返data
    public ResultUtil(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        //like12 add,20210902
        if(status == 0){
            this.result = "success";
        }else{
            this.result = "fail";
        }
    }
    public ResultUtil(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        //like12 add,20210902
        if(status == 0){
            this.result = "success";
        }else{
            this.result = "fail";
        }
    }
    public ResultUtil(Integer status, String msg, Object data, String token) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.token = token;
        //like12 add,20210902
        if(status == 0){
            this.result = "success";
        }else{
            this.result = "fail";
        }
    }
    //like12 add,20210902
    public ResultUtil(Integer status, String result, String msg) {
        this.status = status;
        this.result = result;
        this.msg = msg;
    }
    public ResultUtil(Integer status, String result, String msg, Object data) {
        this.status = status;
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    //成功
    //like12 modified,20210901,只返msg不返data
    public static ResultUtil success(String msg) {
        return new ResultUtil(0, msg);
    }
    public static ResultUtil success(String msg, Object data) {
        return new ResultUtil(0, msg, data);
    }

    //失败
    //like12 modified,20210901,只返msg不返data
    public static ResultUtil fail(String msg) {
        return new ResultUtil(-1, msg);
    }
    public static ResultUtil fail(String msg, Object data) {
        return new ResultUtil(-1, msg, data);
    }
    //like12 add,20220414,支持多种失败返回情况(如：异常)
    public static ResultUtil fail(Integer status, String msg) {
        return new ResultUtil(status, msg);
    }
    public static ResultUtil fail(Integer status, String msg, Object data) {
        return new ResultUtil(status, msg, data);
    }

    //自定义返回
    public static ResultUtil build(Integer status, String msg) {
        return new ResultUtil(status, msg);
    }
    public static ResultUtil build(Integer status, String msg, Object data) {
        return new ResultUtil(status, msg, data);
    }
    public static ResultUtil build(Integer status, String msg, Object data, String token) {
        return new ResultUtil(status, msg, data, token);
    }
    //like12 add,20210902
    public static ResultUtil build(Integer status, String result, String msg) {
        return new ResultUtil(status, result, msg);
    }
    public static ResultUtil build(Integer status, String result, String msg, Object data) {
        return new ResultUtil(status, result, msg, data);
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static ResultUtil format(String json) {
        try {
            return MAPPER.readValue(json, ResultUtil.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将json结果集转化为TaotaoResult对象
     *
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static ResultUtil formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ResultUtil.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static ResultUtil formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}