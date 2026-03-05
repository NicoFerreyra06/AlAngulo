package com.nicoferreyra.alangulo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nicolasferreyramdp@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
        System.out.println("📧 Email enviado a " + to);
    }
}