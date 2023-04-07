package com.example.easybbsweb.domain.entity;

public class Answer {
    private Integer ansId;

    private Integer queId;

    private String content;

    private Byte effect;

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