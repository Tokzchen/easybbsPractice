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
import com.example.easybbsweb.service.UniversityService;
import com.example.easybbsweb.utils.CheckCodeUtils;
import com.example.easybbsweb.utils.RedisUtils;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/user")
@Tag(name = "普通用户账号相关接口")
public class AccountController {
    @Autowired
    SendMailService sendMailService;
    @Autowired
    RegistryService registryService;

    @Autowired
    AccountService accountService;

    @Autowired
    UniversityService universityService;


    @GetMapping("/checkCode")
    @Operation(summary = "获取验证码",description = "根据ip地址获取验证码")
    public ResultInfo createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CheckCodeUtils.generateCheckCodeByRedis(request,response);
        return new ResultInfo(true,"响应成功",null);
    }

    @Operation(summary = "发送邮件验证码")
    @PostMapping("/sendEmailCode")
    public Object verifyAndSendMail(HttpServletRequest req, HttpServletResponse res, @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = Map.class, requiredProperties = {"email"}))) @RequestBody Map map){


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

    @Operation(summary = "内部转发接口，前端勿直接调用")
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
    @Operation(summary = "后端转发接口，前端勿使用")
    public ResultInfo sendHTMLMail(@RequestBody MailRequest mailRequest){
            sendMailService.sendHTMLMail(mailRequest);
            return new ResultInfo(true,"发送成功",null);
    }

    @PostMapping("/registry")
    @Operation(summary = "普通用户注册",description = "至少提供emailCode,password")
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
    @Operation(summary = "普通用户重置密码",description = "至少提供email,emailCode,password(新密码属性)")
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
    @Operation(summary = "普通用户与高校的登录接口",description = "至少提供email,password")
    public ResultInfo userLogin(@RequestBody UserInfo userInfo,HttpServletRequest req,HttpServletResponse res){
        boolean b = CheckCodeUtils.verifyCheckCodeByRedis(req,res,userInfo.getCheckCode());
        University university = new University();
        university.setEmail(userInfo.getEmail());
        university.setPwd(userInfo.getPassword());
        if(!b){
            throw new IncorrectInfoException("验证码错误!");
        }else {

            boolean b1 = accountService.userLogin(userInfo);

            boolean b2 = universityService.UniversityLogin(university);
            if(b1||b2){
                String token =b1? TokenUtil.sign(userInfo):TokenUtil.sign(university);
                //使用Redis进行身份信息缓存
                RedisUtils.set(token+":identity",b1?"user":"university",10*60*60);
                return new ResultInfo(true,b1?"user":"university",token);
            }else{
                return new ResultInfo(false,"用户名或密码错误",null);
            }
        }
    }

        @PostMapping("/identity")
        @Operation(summary = "获取用户身份信息",description = "结果在ResultInfo.data->user/university")
        public ResultInfo getUserIdentity(@RequestHeader("token") String token ){
            //正常情况下redis缓存中都会有
            Object userIdentity = RedisUtils.get(token + ":identity");
            if(userIdentity==null){
                //缓存中没有，进行查表
                Integer integer = accountService.checkUserIdentity(TokenUtil.getCurrentUserOrUniId(token));
                if(integer==0){
                    return new ResultInfo(true,"响应成功","user");
                }else if(integer==1){
                    return new ResultInfo(true,"响应成功","university");
                }
            }
            return new ResultInfo(true,"响应成功",userIdentity);
        }









}
