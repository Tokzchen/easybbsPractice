package com.example.easybbsweb.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;

public class Answer {
    @ExcelProperty("答案编号")
    private Integer ansId;
    @ExcelProperty("所属问题编号")
    private Integer queId;
    @ExcelProperty("答案内容")
    private String content;
    @ExcelProperty("利或不利（-1,0,1）")
    private Byte effect;
    @ExcelProperty("附加内容")
    private String append;

    public Integer getAnsId() {
        return ansId;
    }

    public void setAnsId(Integer ansId) {
        this.ansId = ansId;
    }

    public Integer getQueId() {
        return queId;
    }

    public void setQueId(Integer queId) {
        this.queId = queId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getEffect() {
        return effect;
    }

    public void setEffect(Byte effect) {
        this.effect = effect;
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
        sb.append(", ansId=").append(ansId);
        sb.append(", queId=").append(queId);
        sb.append(", content=").append(content);
        sb.append(", effect=").append(effect);
        sb.append(", append=").append(append);
        sb.append("]");
        return sb.toString();
    }
}