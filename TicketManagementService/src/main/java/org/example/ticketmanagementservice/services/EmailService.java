package org.example.ticketmanagementservice.services;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send an email with a PDF ticket attached.
     *
     * @param to       recipient email
     * @param subject  email subject
     * @param htmlBody email body (HTML)
     * @param pdfBytes ticket PDF bytes
     * @param fileName name of the attached PDF file
     */
    public void sendTicketEmailWithPdf(
            String to,
            String subject,
            String htmlBody,
            byte[] pdfBytes,
            String fileName
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = HTML

            helper.addAttachment(fileName, new ByteArrayResource(pdfBytes));

            mailSender.send(message);
        } catch (MessagingException e) {
            // Log and swallow – you don't want payment to fail just because email failed
            e.printStackTrace();
        }
    }
}
