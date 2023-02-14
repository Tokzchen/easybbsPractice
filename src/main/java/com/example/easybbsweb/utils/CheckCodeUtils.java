package com.example.easybbsweb.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.example.easybbsweb.exception.ExpiredInfoException;
import com.example.easybbsweb.exception.IncorrectInfoException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
@Slf4j
public class CheckCodeUtils {
    public static String generateCheckCode(HttpServletRequest req, HttpServletResponse res){
        //该方法产生一个capture,并将加密后的验证码注入到session当中
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 50, 4, 20);
        //图形验证码写出，可以写出到文件，也可以写出到流
        String returnCode;
        try {
            OutputStream outputStream = new BufferedOutputStream(res.getOutputStream());
            String code = captcha.getCode();
            returnCode= DigestUtils.md5DigestAsHex(code.getBytes(StandardCharsets.UTF_8));
            req.getSession().setAttribute("checkCode",returnCode);
            //验证图形验证码的有效性，返回boolean值
            captcha.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return returnCode;
    }

    public static boolean verifyCheckCode(HttpServletRequest req,HttpServletResponse res,String checkCode){
        String userInput=DigestUtils.md5DigestAsHex(checkCode.getBytes(StandardCharsets.UTF_8));
        boolean result=userInput.equalsIgnoreCase((String) req.getSession().getAttribute("checkCode"));
        return result;
    }

    public static boolean verifyCheckCode(String checkCode,String sessionCodeP){
        String userInput=DigestUtils.md5DigestAsHex(checkCode.trim().getBytes(StandardCharsets.UTF_8));
        boolean result=userInput.equalsIgnoreCase(sessionCodeP);
        return result;
    }

    public static boolean verifyEmailCode(HttpServletRequest req,String emailCode) throws Exception{
        String userInput=DigestUtils.md5DigestAsHex(emailCode.getBytes(StandardCharsets.UTF_8));
        String emailCode1 = (String) req.getSession().getAttribute("emailCode");
        if(emailCode1==null){
            log.info("验证码已过期..");
            throw new ExpiredInfoException("验证码已过期");
        }
        return userInput.equalsIgnoreCase(emailCode1);
    }

    public static boolean verifyEmailCode(String sessionCodeP,String emailCode){
        if(sessionCodeP==null){
            log.error("验证码已过期");
            throw new IncorrectInfoException("验证码过期");
        }
        if(emailCode==null){
            log.warn("验证码输入不得为空");
            throw new IncorrectInfoException("验证码输入不得为空");
        }
        String user_input = DigestUtils.md5DigestAsHex(emailCode.trim().getBytes());
        log.info("从用户处得到验证码{},从session处得到验证码{}",user_input,sessionCodeP);

        return sessionCodeP.equalsIgnoreCase(user_input);
    }
}
