package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.entities.AppUser;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

@Service
public class MailService {

    private static final long CODE_EXPIRATION_TIME_MS = 5 * 60 * 1000; // 5 хвилин у мілісекундах

    private final AppUserDAO appUserDAO;
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


    public MailService(AppUserDAO appUserDAO, JavaMailSender javaMailSender) {
        this.appUserDAO = appUserDAO;
        this.javaMailSender = javaMailSender;
    }

    public ResponseEntity<String> sendCode(String email) {
        Optional<AppUser> optionalAppUser = appUserDAO.findAppUserByEmail(email);
        if (optionalAppUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The email address you entered is not associated with any account.");
        }
        userEmail = email;
        verificationCode = generateRandomCode();
        codeCreationTime = System.currentTimeMillis();
        sendVerificationEmail(verificationCode);

        return ResponseEntity.ok("Verification code sent successfully");
    }

    public ResponseEntity<String> confirmResetCode(String codeConfirm) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - codeCreationTime;
        if (elapsedTime > CODE_EXPIRATION_TIME_MS) {
            verificationCode = null; // код застарів, перевстановлюємо його на null
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Code has expired");
        }
        if (verificationCode.equals(codeConfirm)) { // Порівнюємо збережений код з введеним користувачем
            verificationCode = null;
            return ResponseEntity.ok("Code confirmed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code");
        }
    }

    public void sendVerificationEmail(String code) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            String senderEmail = "authenticator.umarket@gmail.com";
            String senderName = "uMarket";
            mimeMessage.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setTo(userEmail);
            helper.setSubject("Password Reset Verification");
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