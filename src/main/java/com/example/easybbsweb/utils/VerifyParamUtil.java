package com.example.easybbsweb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyParamUtil {
    public static boolean checkValue(String regex,String value){
        if(value==null){
            return false;
        }
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean checkValue(VerifyRegexEnum verifyRegexEnum,String value){
        return checkValue(verifyRegexEnum.getRegex(),value);
    }
}
