package com.example.easybbsweb.domain.entity;

import java.util.Date;

public class LawAid {
    private Long lawAidId;

    private Long userId;

    private Long uniId;

    private String area;

    private String file;

    private Date createTime;

    private String details;

    public Long getLawAidId() {
        return lawAidId;
    }

    public void setLawAidId(Long lawAidId) {
        this.lawAidId = lawAidId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUniId() {
        return uniId;
    }

    public void setUniId(Long uniId) {
        this.uniId = uniId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", lawAidId=").append(lawAidId);
        sb.append(", userId=").append(userId);
        sb.append(", uniId=").append(uniId);
        sb.append(", area=").append(area);
        sb.append(", file=").append(file);
        sb.append(", createTime=").append(createTime);
        sb.append(", details=").append(details);
        sb.append("]");
        return sb.toString();
    }
}