package com.example.easybbsweb.controller;

import com.example.easybbsweb.controller.response.ResultInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qiniu")
@Tag(name="七牛云接口")
public class QiniuController {
    @Value("${qiniu.accessKey}")
    String accessKey;
    @Value("${qiniu.secretKey}")
    String secretKey;
    @Value("${qiniu.bucket}")
    String bucket;
    Configuration cfg = new Configuration(Region.huabei());
    @GetMapping("/get/token")
    @Operation(summary = "获取七牛token")
    public ResultInfo getToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        String data = auth.uploadToken(bucket);
        return new ResultInfo(true,"ok",data);
    }
    @Operation(summary = "删除七牛云上的文件")
    @PostMapping("/delete")
    public ResultInfo deleteImg(
            @Parameter(description = "要删除的文件名") @RequestParam("key")  String key){
        System.out.println(key);
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
        return new ResultInfo(true,"ok",null);
    }
}
