package com.example.easybbsweb.utils;

public enum VerifyRegexEnum {
    NO("", "不校验"),
    EMAIL("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", "邮箱"),
    PASSWORD("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$","密码");
    private String regex;
    private String desc;
    VerifyRegexEnum(String regex, String desc) {
        this.regex = regex;this.desc = desc;
    }

    public String getRegex() {return regex;}
    public void setRegex(String regex) {this.regex = regex;}
    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc = desc;}
}
