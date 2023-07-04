package com.example.easybbsweb.controller;


import com.example.easybbsweb.anotation.GlobalInterceptor;
import com.example.easybbsweb.domain.ResultInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/lawAid")
public class LawAidController {
    /**
     * 该接口是高校认证接口
     * 其次要保证该资料是本高校上传的，所以除了附件之外，前端还需要传入token来确保是原本的高校
     * @return
     */
    @PostMapping("/verify")
    @GlobalInterceptor(checkIsLogin = true)
    public ResultInfo universityVerify(MultipartFile multipartFile, @RequestHeader("token") String token){
        //处理文件上传逻辑



        return null;
    }
}
