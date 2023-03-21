package com.example.easybbsweb.service;

import com.example.easybbsweb.domain.MailRequest;

public interface SendMailService {
    public void sendSimpleMail(MailRequest mailRequest);
    public void sendHTMLMail(MailRequest mailRequest);
}
