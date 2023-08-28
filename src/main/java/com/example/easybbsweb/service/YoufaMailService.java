package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.entity.YoufaMail;


import java.util.List;

public interface YoufaMailService {

    //单发一个消息
    boolean sendOneMail(YoufaMail youfaMail);

    //群发一个消息
    boolean sendMultipleMail(List<Long> uids, YoufaMail youfaMail);


    //向目前在线的所有用户发送公告
    boolean publishAnnouncement(YoufaMail youfaMail);
}
