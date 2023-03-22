package com.example.easybbsweb.service.impl;

import com.example.easybbsweb.domain.MailRequest;
import com.example.easybbsweb.service.SendMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;

@Slf4j
@Service
public class SendMailServiceImpl implements SendMailService {
    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String mailSender;

    private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);

    public void checkMail(MailRequest mailRequest){
        Assert.notNull(mailRequest,"邮件请求不得为空");
        Assert.notNull(mailRequest.getSendTo(),"收件人不得为空");
        Assert.notNull(mailRequest.getSubject(),"邮件不得为空");
        Assert.notNull(mailRequest.getContent(),"邮件内容不得为空");
    }

    @Override
    public void sendSimpleMail(MailRequest mailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        checkMail(mailRequest);
        message.setFrom(mailSender);
        //收件人可以是一个或者多个,多个收件人用，隔开
        message.setTo(mailRequest.getSendTo().split(","));
        message.setSubject(mailRequest.getSubject());
        message.setText(mailRequest.getContent());
        message.setSentDate(new Date());

        //开启一个线程进行发送邮件
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    javaMailSender.send(message);
                    log.info("发送邮件成功{}=>{}",mailSender,mailRequest.getSendTo());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void sendHTMLMail(MailRequest mailRequest) {
        MimeMessage message = javaMailSender.createMimeMessage();
        checkMail(mailRequest);
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(mailSender);
            helper.setTo(mailRequest.getSendTo());
            helper.setSubject(mailRequest.getSubject());
            helper.setText(mailRequest.getContent(),true);
            helper.setSentDate(new Date());
            String filePath = mailRequest.getFilePath();
            if (StringUtils.hasText(filePath)) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                helper.addAttachment(fileName,file);
            }
            javaMailSender.send(message);
            logger.info("发送邮件成功:{}->{}",mailSender,mailRequest.getSendTo());
        } catch (MessagingException e) {
            log.error("发送邮件出现异常，有可能是文件路径出错",e);
        }
    }
}
