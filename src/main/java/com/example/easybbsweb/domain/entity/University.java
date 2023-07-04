package com.example.easybbsweb.domain.entity;

import com.example.easybbsweb.anotation.VerifyParam;
import com.example.easybbsweb.utils.VerifyRegexEnum;

public class University {
    private Integer id;
    @VerifyParam(required = true)
    private String uniId;

    private String uniName;

    private String pwd;

    private String province;

    private String city;
    @VerifyParam(regex=VerifyRegexEnum.EMAIL)
    private String email;

    private String phone;

    private Byte verified;

    private String file;

    private String emailCode;

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniId() {
        return uniId;
    }

    public void setUniId(String uniId) {
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

    @Override
    public String toString() {
        return "University{" +
                "id=" + id +
                ", uniId='" + uniId + '\'' +
                ", uniName='" + uniName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", verified=" + verified +
                ", file='" + file + '\'' +
                ", emailCode='" + emailCode + '\'' +
                '}';
    }
}