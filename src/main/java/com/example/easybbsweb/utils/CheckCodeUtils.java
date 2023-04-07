package com.example.easybbsweb.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.example.easybbsweb.exception.ExpiredInfoException;
import com.example.easybbsweb.exception.IncorrectInfoException;
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

    @Deprecated
    //将使用redis的缓存方式代替，不再使用session进行缓存
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

            log.info("向session中存入{}的md5:{}",code,(String)req.getSession().getAttribute("checkCode"));
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return returnCode;
    }

    public static String generateCheckCodeByRedis(HttpServletRequest req,HttpServletResponse res){
        //该方法产生一个capture,并将加密后的验证码注入到session当中
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数

        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 50, 4, 20);
        //图形验证码写出，可以写出到文件，也可以写出到流
        String code;
        try {
            OutputStream outputStream = new BufferedOutputStream(res.getOutputStream());
            code = captcha.getCode();
            //这里使用redis进行缓存
            RedisUtils.set(req.getRemoteAddr()+":checkCode",code);
            //验证码5分钟过期
            RedisUtils.expire(req.getRemoteAddr()+":checkCode",60*5);
            log.info("设置redis缓存{}",(String)RedisUtils.get(req.getRemoteAddr()+":checkCode"));
            //验证图形验证码的有效性，返回boolean值
            captcha.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return code;
    }


    @Deprecated
    //将采用redis的方式进行验证
    public static boolean verifyCheckCode(HttpServletRequest req,HttpServletResponse res,String checkCode){
        String userInput=DigestUtils.md5DigestAsHex(checkCode.getBytes(StandardCharsets.UTF_8));
        log.info("用户输入的验证码是{},对应的md5是{}",checkCode,userInput);
        log.info("此时session存的md5是{}",(String) req.getSession().getAttribute("checkCode"));
        boolean result=userInput.equalsIgnoreCase((String) req.getSession().getAttribute("checkCode"));
        return result;
    }

    @Deprecated
    //采用redis方式进行验证，不再推荐使用此方法
    public static boolean verifyCheckCode(String checkCode,String sessionCodeP){
        String userInput=DigestUtils.md5DigestAsHex(checkCode.trim().getBytes(StandardCharsets.UTF_8));
        boolean result=userInput.equalsIgnoreCase(sessionCodeP);
        return result;
    }

    public static boolean verifyCheckCodeByRedis(HttpServletRequest request,HttpServletResponse response,String checkCode){
        String value = (String) RedisUtils.get(request.getRemoteAddr() + ":checkCode");
        if(value==null){
            throw new ExpiredInfoException("验证码已过期");
        }
        return checkCode.equalsIgnoreCase(value);
    }
    @Deprecated
    public static boolean verifyEmailCode(HttpServletRequest req,String emailCode) throws Exception{
        String userInput=DigestUtils.md5DigestAsHex(emailCode.getBytes(StandardCharsets.UTF_8));
        String emailCode1 = (String) req.getSession().getAttribute("emailCode");
        if(emailCode1==null){
            log.info("验证码已过期..");
            throw new ExpiredInfoException("验证码已过期");
        }
        return userInput.equalsIgnoreCase(emailCode1);
    }

    public static boolean verifyEmailCodeByRedis(HttpServletRequest req,String emailCode) throws Exception{
        String realEmailCode = (String)RedisUtils.get(req.getRemoteAddr() + ":emailCode");
        if(realEmailCode==null){
            throw new ExpiredInfoException("验证码已过期");
        }
        return emailCode.equalsIgnoreCase(realEmailCode);
    }
    @Deprecated
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
