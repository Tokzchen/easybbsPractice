package com.example.easybbsweb.domain.entity;

import java.util.Date;

public class UserMain {
    private Integer id;

    private Long userId;

    private Integer aidId;

    private Integer lawyerId;

    private Date createTime;

    private String emailCode;

    private String checkCode;

    private Date lastUpdateTime;

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

    public Integer getAidId() {
        return aidId;
    }

    public void setAidId(Integer aidId) {
        this.aidId = aidId;
    }

    public Integer getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(Integer lawyerId) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", aidId=").append(aidId);
        sb.append(", lawyerId=").append(lawyerId);
        sb.append(", createTime=").append(createTime);
        sb.append(", emailCode=").append(emailCode);
        sb.append(", checkCode=").append(checkCode);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append("]");
        return sb.toString();
    }
}