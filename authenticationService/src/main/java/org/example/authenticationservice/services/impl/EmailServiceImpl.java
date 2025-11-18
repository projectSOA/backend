package org.example.authenticationservice.services.impl;

import org.example.authenticationservice.services.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendPasswordEmail(String toEmail,String password){
        SimpleMailMessage message =  new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Account Password");
        message.setText("Hello,\n\nYour account has been created. Your password is: " + password + "\n\nPlease change it after logging in.");
        mailSender.send(message);
    }
}
