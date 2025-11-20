package org.example.authenticationservice.services.impl;

import org.example.authenticationservice.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url:http://localhost:5137}")
    private String baseUrl;

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

    public void sendConfirmationEmail(String toEmail,String confirmationToken){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Confirm Your Email Address");

            String confirmationUrl = baseUrl + "/confirm-email?token=" + confirmationToken+"?email="+toEmail;

            message.setText("Hello,\n\n" +
                    "Thank you for registering. Please confirm your email address by clicking the link below:\n\n" +
                    confirmationUrl + "\n\n" +
                    "This link will expire in 24 hours.\n\n" +
                    "If you didn't create an account, please ignore this email.");

            mailSender.send(message);

    }
}
