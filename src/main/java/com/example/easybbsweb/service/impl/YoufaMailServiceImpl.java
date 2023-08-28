package com.example.easybbsweb.service.impl;


import com.example.easybbsweb.domain.entity.YoufaMail;
import com.example.easybbsweb.mapper.YoufaMailMapper;
import com.example.easybbsweb.service.YoufaMailService;
import com.example.easybbsweb.utils.RedisUtils;
import com.example.easybbsweb.websocket.WebSocketServer;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class YoufaMailServiceImpl implements YoufaMailService {
    private static final String ANNOUNCE_KEY_REDIS="youfa:notice";

    @Resource
    WebSocketServer webSocketServer;
    @Resource
    YoufaMailMapper youfaMailMapper;

    @Override
    public boolean sendOneMail(YoufaMail youfaMail) {
        if(!checkIsLegal(youfaMail)){
            return false;
        }
        //插入邮件表
        if(youfaMail.getSendTime()==null)youfaMail.setSendTime(new Date());
        if(youfaMail.getType()==null)youfaMail.setType("0");
        youfaMail.setChecked("0");
        int i = youfaMailMapper.insertSelective(youfaMail);
        if(i==0){
            return false;
        }
        webSocketServer.sendOneMail(youfaMail.getReceiver());
        return true;
    }

    @Override
    public boolean sendMultipleMail(List<Long> uids, YoufaMail youfaMail) {
        checkMultiIsLegal(youfaMail);
        List<YoufaMail> mails=new ArrayList<>();
        preProcess(youfaMail);
        //预处理成List准备群发
        for(Long uid:uids){
            YoufaMail temp = new YoufaMail();
            BeanUtils.copyProperties(youfaMail,temp);
            temp.setReceiver(uid);
            mails.add(temp);
        }
        youfaMailMapper.insertMultipleSelective(mails);
        webSocketServer.sendMultiPleMail(uids);
        return true;
    }

    @Override
    public boolean publishAnnouncement(YoufaMail youfaMail) {
        boolean b = checkMultiIsLegal(youfaMail);
        if(!b){
            return false;
        }
        preProcess(youfaMail);
        RedisUtils.setPersistently(ANNOUNCE_KEY_REDIS,youfaMail);
        webSocketServer.publishAnnouncement();
        return true;
    }

    //单发邮件
    protected boolean checkIsLegal(YoufaMail youfaMail){
        if(youfaMail.getSender()==null||youfaMail.getReceiver()==null){
            return false;
        }
        return true;
    }
    //群发邮件
    protected boolean checkMultiIsLegal(YoufaMail youfaMail){
        return youfaMail.getSendTime()!=null;
    }

    protected void preProcess(YoufaMail youfaMail){
        if(!StringUtils.hasText(youfaMail.getType())){
            youfaMail.setType("0");
        }
        if(!StringUtils.hasText(youfaMail.getChecked())){
            youfaMail.setChecked("0");
        }
        if(youfaMail.getSendTime()==null){
            youfaMail.setSendTime(new Date());
        }
    }
}
