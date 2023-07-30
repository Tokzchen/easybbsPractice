package com.example.easybbsweb.controller;



import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.domain.entity.University;
import com.example.easybbsweb.domain.entity.UserInfo;
import com.example.easybbsweb.domain.others.LawAidInfoPageUser;
import com.example.easybbsweb.exception.SystemException;
import com.example.easybbsweb.service.LawAidService;
import com.example.easybbsweb.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/lawAid")
@Tag(name="法律援助业务的接口")
public class LawAidController {

    @Autowired
    LawAidService lawAidService;
    /**
     * 该接口是高校认证接口
     * 其次要保证该资料是本高校上传的，所以除了附件之外，前端还需要传入token来确保是原本的高校
     * @return
     */

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

    @PostMapping("/verify")
    @Operation(summary = "上传认证高校资格的文件",description = "返回认证资料的文件夹路径")
    public ResultInfo universityVerify(MultipartFile file, @RequestHeader("token") String token,HttpServletRequest req){
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
        String parent = jarF.getParentFile().toString()+"/classes/static/universityVerify/"+ TokenUtil.getCurrentUserOrUniId(token)+"/";
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
            url=req.getScheme()+"://"+ req.getServerName()+":"+req.getServerPort()+"/api/universityVerify/"+TokenUtil.getCurrentUserOrUniId(token)+"/";
            //文件存储成功后要在数据库中保存所存储的文件夹的路径
            University university = new University();
            university.setUniId(Long.parseLong(TokenUtil.getCurrentUserOrUniId(token)));
            university.setFile(url);
            boolean b = lawAidService.saveDocumentPath(university);

            if(!b){
                throw new SystemException("保存认证资料失败");
            }else{
                log.info("保存{}的认证资料到url:{}",TokenUtil.getCurrentUserOrUniId(token),url);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResultInfo(true,"上传成功",url);
    }


    @PostMapping("/recommend/user")
    public List<UserInfo> recommendUser(@RequestHeader("token") String token){

        return null;

    }

    @PostMapping("lawAidInfo/user")
    @Operation(summary = "获取法律援助相关个人信息",description = "")
    public ResultInfo getUserLawAidInfo(@RequestHeader("token") String token){
        String currentUserOrUniId = TokenUtil.getCurrentUserOrUniId(token);
        Long userId=Long.parseLong(currentUserOrUniId);

        LawAidInfoPageUser userLawAidInfo = lawAidService.getUserLawAidInfo(userId);
        return new ResultInfo(true,"响应成功",userLawAidInfo);

    }


}
