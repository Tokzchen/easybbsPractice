package com.example.easybbsweb.controller;

import com.example.easybbsweb.controller.response.ResultInfo;
import com.example.easybbsweb.domain.entity.YoufaMail;
import com.example.easybbsweb.service.YoufaMailService;
import com.example.easybbsweb.websocket.WebSocketServer;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@Tag(name="websocket相关的中转controller")
public class WebSocketController {
    @Resource
    WebSocketServer webSocketServer;
    @Resource
    YoufaMailService youfaMailService;
    @PostMapping("/send/one")
    public ResultInfo receiveUserInfo(@RequestBody YoufaMail youfaMail, @RequestHeader("token")String token){
        //发送一封邮件给某个用户
        //检查参数
        if(!isLegal(youfaMail)){
            return ResultInfo.Fail("缺失邮件信息");
        }
        boolean b = youfaMailService.sendOneMail(youfaMail);
        return b? ResultInfo.OK():ResultInfo.Fail();
    }

    protected boolean isLegal(YoufaMail youfaMail){
        if(youfaMail.getSender()==null||youfaMail.getReceiver()==null){
            return false;
        }
        return true;
    }
}
