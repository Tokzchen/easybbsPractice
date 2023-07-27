package com.example.easybbsweb.controller;

import cn.hutool.core.util.RandomUtil;
import com.example.easybbsweb.domain.MailRequest;
import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.IncorrectInfoException;
import com.example.easybbsweb.exception.SystemException;
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
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.easybbsweb.controller.UniversityController.AVATAR_MAX_SIZE;


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

    public static final List<String> AVATAR_TYPES = new ArrayList<String>();

    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/jpg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
        AVATAR_TYPES.add("image/pjpeg");
        AVATAR_TYPES.add("image/x-png");

    }


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


    @Operation(summary = "用户上传上传头像",description = "返回头像url")
    @PostMapping("/avatarUpload")
    public ResultInfo userAvatarUpload(MultipartFile file, @RequestHeader("token") String token, HttpServletRequest req){
        //处理文件上传逻辑

        log.info("客户端尝试上传文件中，连接接口成功...");
        if(file.isEmpty()){
            return new ResultInfo(false,"文件不得为空",null);
        }
        if(file.getSize()>AVATAR_MAX_SIZE){
            return new ResultInfo(false,"文件太大啦",null);
        }
        String contentType = file.getContentType();
        // boolean contains(Object o)：当前列表若包含某元素，返回结果为true；若不包含该元素，返回结果为false
        if (!AVATAR_TYPES.contains(contentType)) {
            // 是：抛出异常
            return new ResultInfo(false,"不支持使用该类型的文件作为头像，允许的文件类型：" + AVATAR_TYPES,null);
        }

        //获取jar包所在目录
        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        //在jar包所在目录下生成一个文件夹用来存储上传的图片,子目录就是大学id
        String parent = jarF.getParentFile().toString()+"/classes/static/userAvatar/"+ TokenUtil.getCurrentUserOrUniId(token)+"/";
        System.out.println(parent);

        // 保存头像文件的文件夹
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex > 0) {
            suffix = originalFilename.substring(beginIndex);
        }

        String newName= UUID.randomUUID().toString()+suffix;
        System.out.println(newName);
        String url="";
        try {
            file.transferTo(new File(dir,newName));
            url=req.getScheme()+"://"+ req.getServerName()+":"+req.getServerPort()+"/api/userAvatar/"+TokenUtil.getCurrentUserOrUniId(token)+"/"+newName;
            //文件存储成功后要在数据库中保存所存储的文件夹的路径
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(Long.parseLong(TokenUtil.getCurrentUserOrUniId(token)));
            //此处的url与资料认证处的不同，具体到文件名，资料认证处是具体到文件夹
            userInfo.setAvatar(url);
            boolean b = accountService.saveUserAvatarPath(userInfo);
            if(!b){
                throw new SystemException("保存用户头像失败");
            }else{
                log.info("保存{}的用户头像到url:{}",TokenUtil.getCurrentUserOrUniId(token),url);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResultInfo(true,"上传成功",url);

    }

    @Operation(summary = "获取用户头像头像url")
    @PostMapping("/getAvatar")
    public ResultInfo getUserAvatar( @RequestHeader("token") String token){
        //根据token获取高校的id
        String userId = TokenUtil.getCurrentUserOrUniId(token);
        if(!StringUtils.hasText(userId)){
            throw new BusinessException("用户不存在或token已失效");
        }

        UserInfo userInfoByUserId = accountService.getUserInfoByUserId(Long.parseLong(userId));
        return new ResultInfo(true,"响应成功",userInfoByUserId.getAvatar());
    }


    @Operation(summary = "获取用户全部信息")
    @PostMapping("/infos")
    public ResultInfo getUserInfo( @RequestHeader("token") String token){
        //根据token获取高校的id
        String userId = TokenUtil.getCurrentUserOrUniId(token);
        if(!StringUtils.hasText(userId)){
            throw new BusinessException("用户不存在或token已失效");
        }

        UserInfo userInfoByUserId = accountService.getUserInfoByUserId(Long.parseLong(userId));
        userInfoByUserId.setPassword(null);
        userInfoByUserId.setEmailCode(null);
        return new ResultInfo(true,"响应成功",userInfoByUserId);
    }








}
