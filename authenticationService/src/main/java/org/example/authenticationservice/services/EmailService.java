package org.example.authenticationservice.services;

public interface EmailService {
    void sendPasswordEmail(String toEmail,String password);
    public void sendConfirmationEmail(String toEmail,String confirmationToken);
}
