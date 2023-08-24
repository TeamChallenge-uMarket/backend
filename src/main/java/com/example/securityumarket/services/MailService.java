package com.example.securityumarket.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
public class MailService {

    public static final long CODE_EXPIRATION_TIME_MS = 5 * 60 * 1000; // 5 minutes

    private final JavaMailSender javaMailSender;
    private String verificationCode;

    private String userEmail;
    private long codeCreationTime;

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public long getCodeCreationTime() {
        return codeCreationTime;
    }

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendCode(String email) {
        userEmail = email;
        verificationCode = generateRandomCode();
        codeCreationTime = System.currentTimeMillis();
        sendVerificationEmail(verificationCode, email);
        ResponseEntity.ok("Verification code sent successfully");
    }

    public void sendVerificationEmail(String code, String userEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            String senderEmail = "authenticator.umarket@gmail.com";
            String senderName = "uMarket";
            mimeMessage.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setTo(userEmail);
            helper.setSubject("Verification");
            helper.setText("Your verification code: " + code, false);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        return String.valueOf(randomNumber);
    }
}