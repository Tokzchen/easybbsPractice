package com.example.easybbsweb.controller;

import com.example.easybbsweb.domain.ResultInfo;
import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.SystemException;
import com.example.easybbsweb.service.RegistryService;
import com.example.easybbsweb.service.UniversityService;
import com.example.easybbsweb.utils.CheckCodeUtils;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/university")
@Slf4j
public class UniversityController {

    /** 头像文件大小的上限值(1MB) */
    public static final int AVATAR_MAX_SIZE = 1 * 1024 * 1024;
    /** 允许上传的头像的文件类型 */
    public static final List<String> AVATAR_TYPES = new ArrayList<String>();

    /** 初始化允许上传的头像的文件类型 */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/jpg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
        AVATAR_TYPES.add("image/pjpeg");
        AVATAR_TYPES.add("image/x-png");

    }

    @Autowired
    RegistryService registryService;

    @Autowired
    UniversityService universityService;
    @PostMapping("/uniRegistry")
    public ResultInfo universityRegistry(@RequestBody University university, HttpServletRequest req){
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
            return new ResultInfo(true,"注册成功",token);
        }else{
            return new ResultInfo(false,"注册失败",null);
        }
    }

    @PostMapping("/avatarUpload")
    public ResultInfo universityVerify(MultipartFile file, @RequestHeader("token") String token, HttpServletRequest req){
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
        String parent = jarF.getParentFile().toString()+"/classes/static/universityAvatar/"+ TokenUtil.getCurrentUserOrUniId(token)+"/";
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
            url=req.getScheme()+"://"+ req.getServerName()+":"+req.getServerPort()+"/api/universityVerify/"+TokenUtil.getCurrentUserOrUniId(token)+"/"+newName;
            //文件存储成功后要在数据库中保存所存储的文件夹的路径
            University university = new University();
            university.setUniId(TokenUtil.getCurrentUserOrUniId(token));
            //此处的url与资料认证处的不同，具体到文件名，资料认证处是具体到文件夹
            university.setFile(url);
            boolean b = universityService.saveUniversityAvatarPath(university);
            if(!b){
                throw new SystemException("保存高校头像失败");
            }else{
                log.info("保存{}的高校头像到url:{}",TokenUtil.getCurrentUserOrUniId(token),url);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResultInfo(true,"上传成功",url);

    }
}
