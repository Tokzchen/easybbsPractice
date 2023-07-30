package com.example.easybbsweb.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum YouFaExceptionStatusCode {
    ELASTIC_SEARCH_ERROR(1000,"ES服务器异常"),
    ELASTIC_SYNCHRONIZED_ERROR(1001,"mongo数据修改没有同步到ES数据库")
    ;
    public final int code;
    public final String msg;

}
