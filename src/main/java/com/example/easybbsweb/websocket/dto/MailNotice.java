package com.example.easybbsweb.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailNotice {
    Boolean flag;
    String type;
    public static MailNotice isSimple(){
        return new MailNotice(true,"0");
    }

    public static MailNotice isAnnouncement(){
        return new MailNotice(true,"1");
    }
}
