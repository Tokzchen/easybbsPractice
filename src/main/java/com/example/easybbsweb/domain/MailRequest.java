package com.example.easybbsweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {
    private String sendTo;
    private String subject;
    private String content;
    private String filePath;
}
