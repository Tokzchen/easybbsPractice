package com.example.easybbsweb.controller;


import cn.hutool.core.util.RandomUtil;
import com.example.easybbsweb.domain.MailRequest;
import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.service.AccountService;
import com.example.easybbsweb.service.RegistryService;
import com.example.easybbsweb.service.SendMailService;
import com.example.easybbsweb.utils.CheckCodeUtils;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class AccountController {

    @Autowired
    SendMailService sendMailService;

    @Autowired
    AccountService accountService;

    @Autowired
    RegistryService registryService;

    @GetMapping("/checkCode")
    public ResultInfo createCode(String captchaId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        CheckCodeUtils.generateCheckCode(request,response);
        return new ResultInfo(true,"响应成功",null);
    }


    @PostMapping("/login")
    public ResultInfo userLogin(@RequestBody UserInfo userInfo,HttpServletRequest req){
        String sessionP = (String) req.getSession().getAttribute("checkCode");
        boolean b = CheckCodeUtils.verifyCheckCode(userInfo.getCheckCode(), sessionP);
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

    @PostMapping("/registry")
    public ResultInfo registerUser(@RequestBody UserInfo userInfo,HttpServletRequest req){
        if(userInfo.getEmailCode()==null||userInfo.getEmailCode().equals("")){
            return new ResultInfo(false,"验证码不得为空",null);
        }
        try {
            boolean b = CheckCodeUtils.verifyEmailCode(req, userInfo.getEmailCode().trim());
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


    @PostMapping("/sendEmailCode")
    public Object verifyAndSendMail(HttpServletRequest req, HttpServletResponse res, @RequestBody Map map){
            if(map.get("email")==null||map.get("email").equals("")){
                return new ResultInfo(false,"邮箱不得为空",null);
            }

            log.info("验证码验证成功准备发送邮件....");
            //生成邮箱验证码并加密存入到req session当中
            String s = RandomUtil.randomNumbers(5);
            String mailCodeP = DigestUtils.md5DigestAsHex(s.trim().getBytes());
            req.setAttribute("mailCodeN",s);
            req.setAttribute("email",map.get("email"));
            req.getSession().setAttribute("emailCode",mailCodeP);
            log.info("准备转发实现发送邮件....");
            return new ModelAndView("forward:/user/send-mail/simple");
    }

    @PostMapping("/send-mail/simple")
    public ResultInfo sendSimpleMail(@RequestAttribute("email") String sendTo,@RequestAttribute("mailCodeN") String contentCode,HttpServletRequest req){
        MailRequest mailRequest = new MailRequest(
                sendTo,
                "邮件验证码",
                "您的验证码是: " + contentCode + " \n验证码30分钟内有效！",
                null);

        sendMailService.sendSimpleMail(mailRequest);
        log.info("发送邮箱验证码完毕，即将向页面返回数据");

        return new ResultInfo(true,"发送成功！",null);
    }

    //用于发送复杂邮件,比如可以带附件
    @PostMapping("/send-mail/html")
    public ResultInfo sendHTMLMail(@RequestBody MailRequest mailRequest){
        sendMailService.sendHTMLMail(mailRequest);
        return new ResultInfo(true,"发送成功",null);
    }

}
