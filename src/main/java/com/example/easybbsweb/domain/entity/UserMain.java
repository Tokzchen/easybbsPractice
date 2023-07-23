package com.example.easybbsweb.domain.entity;

import java.util.Date;

public class UserMain {
    private Integer id;

    private Long userId;

    private Long uniId;

    private Long lawyerId;

    private Date createTime;

    private String emailCode;

    private String checkCode;

    private Date lastUpdateTime;

    private String area;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(Long lawyerId) {
        this.lawyerId = lawyerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", uniId=").append(uniId);
        sb.append(", lawyerId=").append(lawyerId);
        sb.append(", createTime=").append(createTime);
        sb.append(", emailCode=").append(emailCode);
        sb.append(", checkCode=").append(checkCode);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", area=").append(area);
        sb.append("]");
        return sb.toString();
    }
}