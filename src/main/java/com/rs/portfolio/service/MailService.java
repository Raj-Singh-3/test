
package com.rs.portfolio.service;

import com.rs.portfolio.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${owner.email}")
    private String ownerEmail;

    @Value("${BACKEND_URL}")
    private String backendUrl;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private final Map<String, ContactRequest> pendingMessages = new HashMap<>();

    public void sendVerificationMail(ContactRequest request) {

        String token = UUID.randomUUID().toString();

        pendingMessages.put(token, request);

        String verifyLink = backendUrl + "/api/contact/verify?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(request.getEmail());

        mail.setSubject("Verify your Email");

        mail.setText("""
                Hi %s,

                Please verify your email by clicking the link below.

                %s

                If you didn't submit this form, ignore this mail.
                """.formatted(request.getName(), verifyLink));

        mailSender.send(mail);

    }

    public String verify(String token) {

        ContactRequest request = pendingMessages.get(token);

        if (request == null)
            return "Invalid or Expired Link";

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(ownerEmail);

        mail.setSubject(request.getSubject());

        mail.setText("""
                New Portfolio Contact

                Name : %s

                Email : %s

                Message :

                %s
                """.formatted(
                request.getName(),
                request.getEmail(),
                request.getMessage()
        ));

        mailSender.send(mail);

        pendingMessages.remove(token);

        return "Email Verified Successfully. Your message has been sent.";

    }

}
