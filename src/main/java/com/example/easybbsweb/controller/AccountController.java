package com.example.easybbsweb.controller;

import cn.hutool.core.util.RandomUtil;
import com.example.easybbsweb.anotation.GlobalInterceptor;
import com.example.easybbsweb.anotation.VerifyParam;
import com.example.easybbsweb.domain.MailRequest;
import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.service.RegistryService;
import com.example.easybbsweb.service.SendMailService;
import com.example.easybbsweb.utils.CheckCodeUtils;
import com.example.easybbsweb.utils.RedisUtils;
import com.example.easybbsweb.utils.ResultUtil;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class AccountController {
    @Autowired
    SendMailService sendMailService;
    @Autowired
    RegistryService registryService;

    @Autowired
    AccountService accountService;


    @GetMapping("/checkCode")
    public ResultInfo createCode(String captchaId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        CheckCodeUtils.generateCheckCodeByRedis(request,response);
        return new ResultInfo(true,"响应成功",null);
    }
    @PostMapping("/sendEmailCode")
    public Object verifyAndSendMail(HttpServletRequest req, HttpServletResponse res, @RequestBody Map map){


            log.info("验证码验证成功准备发送邮件....");
            //生成邮箱验证码并加密存入到req session当中
            String s = RandomUtil.randomNumbers(5);
            //将邮箱验证码缓存进redis
            RedisUtils.set(req.getRemoteAddr()+":emailCode",s);
            RedisUtils.expire(req.getRemoteAddr()+":emailCode",60*15);
            log.info("准备转发实现发送邮件....");
            MailRequest mailRequest = new MailRequest(
                map.get("email").toString(),
                "邮件验证码",
                "您的验证码是: " + s + " \n验证码15分钟内有效！",
                null);

        sendMailService.sendSimpleMail(mailRequest);

        return new ResultInfo(true,"发送成功！邮件可能有延迟，请耐心等待。",null);
    }

    @PostMapping("/send-mail/simple")
    public ResultInfo sendSimpleMail(@RequestAttribute("email") String sendTo,@RequestAttribute("mailCodeN") String contentCode,HttpServletRequest req){
        MailRequest mailRequest = new MailRequest(
                sendTo,
                "邮件验证码",
                "您的验证码是: " + contentCode + " \n验证码15分钟内有效！",
                null);

        sendMailService.sendSimpleMail(mailRequest);

            return new ResultInfo(true,"发送成功！邮件可能有延迟，请耐心等待。",null);
    }

    //用于发送复杂邮件,比如可以带附件
    @PostMapping("/send-mail/html")
    public ResultInfo sendHTMLMail(@RequestBody MailRequest mailRequest){
            sendMailService.sendHTMLMail(mailRequest);
            return new ResultInfo(true,"发送成功",null);
    }

    @PostMapping("/registry")
    public ResultInfo registerUser(@RequestBody UserInfo userInfo,HttpServletRequest req){
        if(userInfo.getEmailCode()==null||userInfo.getEmailCode().equals("")){
            return new ResultInfo(false,"验证码不得为空",null);
        }
        try {
            boolean b = CheckCodeUtils.verifyEmailCodeByRedis(req, userInfo.getEmailCode());
            if(!b){
                return new ResultInfo(false,"验证码错误",null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        boolean b = registryService.registerUser(userInfo);
        if(b){
            return new ResultInfo(true,"注册成功",null);
        }else{
            return new ResultInfo(false,"注册失败",null);
        }

    }

    @PostMapping("/resetPwd")
    public ResultInfo resetPwd(@RequestBody UserInfo userInfo,HttpServletRequest req){
        boolean b = accountService.resetPwd(userInfo, req);
        if(b){
            return new ResultInfo(true,"修改成功，请使用新密码登录",null);
        }
        else{
            return new ResultInfo(false,"修改失败，请稍后重试",null);
        }

    }

    @PostMapping("/login")
    public ResultInfo userLogin(@RequestBody UserInfo userInfo,HttpServletRequest req,HttpServletResponse res){
        boolean b = CheckCodeUtils.verifyCheckCodeByRedis(req,res,userInfo.getCheckCode());
        if(!b){
            throw new IncorrectInfoException("验证码错误!");
        }else {
            boolean b1 = accountService.userLogin(userInfo);
            if(b1){
                String token = TokenUtil.sign(userInfo);
                return new ResultInfo(true,"登录成功",token);
            }else{
                return new ResultInfo(false,"用户名或密码错误",null);
            }

        }

    }


    @PostMapping("/uniRegistry")
    public ResultUtil universityRegistry(@RequestBody  University university,HttpServletRequest req){
        //先check一下验证码
        if(university.getEmailCode()==null||university.getEmailCode().equals("")){
            throw new BusinessException("邮箱验证码不得为空");
        }

        try {
            boolean b = CheckCodeUtils.verifyEmailCodeByRedis(req, university.getEmailCode());
            if(!b){
                throw new BusinessException("验证码错误");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        boolean b = registryService.registerUniversity(university);
        if(b){
            //注册成功的下一步是上传资料，此时已经属于已登录的状态
            //到这一步我们就算登录成功，因此需要注册token,将token返回给前端
            String token = TokenUtil.sign(university);
            return ResultUtil.build(0,"注册普通账号成功",token);
        }else{
            return ResultUtil.build(1,"似乎发生了一些问题");
        }
    }


}
