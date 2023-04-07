package com.example.easybbsweb.domain.entity;

import java.math.BigDecimal;

public class TestRecord {
    private Integer id;

    private String userId;

    private Integer negCount;

    private Integer medCount;

    private Integer posCount;

    private String tracePath;

    private String reportContent;

    private BigDecimal postion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getNegCount() {
        return negCount;
    }

    public void setNegCount(Integer negCount) {
        this.negCount = negCount;
    }

    public Integer getMedCount() {
        return medCount;
    }

    public void setMedCount(Integer medCount) {
        this.medCount = medCount;
    }

    public Integer getPosCount() {
        return posCount;
    }

    public void setPosCount(Integer posCount) {
        this.posCount = posCount;
    }

    public String getTracePath() {
        return tracePath;
    }

    public void setTracePath(String tracePath) {
        this.tracePath = tracePath;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public BigDecimal getPostion() {
        return postion;
    }

    public void setPostion(BigDecimal postion) {
        this.postion = postion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", negCount=").append(negCount);
        sb.append(", medCount=").append(medCount);
        sb.append(", posCount=").append(posCount);
        sb.append(", tracePath=").append(tracePath);
        sb.append(", reportContent=").append(reportContent);
        sb.append(", postion=").append(postion);
        sb.append("]");
        return sb.toString();
    }
}