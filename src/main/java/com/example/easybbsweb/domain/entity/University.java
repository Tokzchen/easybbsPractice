package com.example.easybbsweb.domain.entity;

public class University {
    private Long uniId;

    private String uniName;

    private String pwd;

    private String province;

    private String city;

    private String email;

    private String phone;

    private Byte verified;

    private String file;

    private String avatar;

    private String emailCode;

    private String lawLevel;

    private String website;

    public Long getUniId() {
        return uniId;
    }

    public void setUniId(Long uniId) {
        this.uniId = uniId;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getVerified() {
        return verified;
    }

    public void setVerified(Byte verified) {
        this.verified = verified;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public String getLawLevel() {
        return lawLevel;
    }

    public void setLawLevel(String lawLevel) {
        this.lawLevel = lawLevel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uniId=").append(uniId);
        sb.append(", uniName=").append(uniName);
        sb.append(", pwd=").append(pwd);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", verified=").append(verified);
        sb.append(", file=").append(file);
        sb.append(", avatar=").append(avatar);
        sb.append(", emailCode=").append(emailCode);
        sb.append(", lawLevel=").append(lawLevel);
        sb.append(", website=").append(website);
        sb.append("]");
        return sb.toString();
    }
}