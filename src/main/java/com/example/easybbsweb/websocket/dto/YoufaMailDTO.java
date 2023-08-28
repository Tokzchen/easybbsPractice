package com.example.easybbsweb.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoufaMailDTO {
    //邮件id
    Long id;
    //发送人id
    Long sender;
    //接收方id
    Long receiver;
    //接收方是否已经查了该邮件
    String checked;
    //0-text 1-html
    String type;
    //文字内容
    String content;
    //附件资源链接
    String attachUrl;
    //发送时间
    Date sendTime;
}
