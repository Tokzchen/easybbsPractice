package com.example.easybbsweb.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;

public class Question {
    @ExcelProperty("问题编号")
    private Integer queId;
    @ExcelProperty("所属答案编号")
    private Integer ansId;
    @ExcelProperty("问题内容")
    private String content;
    @ExcelProperty("问题优先级（-1,0,1）")
    private Byte importance;
    @ExcelProperty("附加内容")
    private String append;

    public Integer getQueId() {
        return queId;
    }

    public void setQueId(Integer queId) {
        this.queId = queId;
    }

    public Integer getAnsId() {
        return ansId;
    }

    public void setAnsId(Integer ansId) {
        this.ansId = ansId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getImportance() {
        return importance;
    }

    public void setImportance(Byte importance) {
        this.importance = importance;
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", queId=").append(queId);
        sb.append(", ansId=").append(ansId);
        sb.append(", content=").append(content);
        sb.append(", importance=").append(importance);
        sb.append(", append=").append(append);
        sb.append("]");
        return sb.toString();
    }
}