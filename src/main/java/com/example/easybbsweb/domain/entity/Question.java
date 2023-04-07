package com.example.easybbsweb.domain.entity;

public class Question {
    private Integer queId;

    private Integer ansId;

    private String content;

    private Byte importance;

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