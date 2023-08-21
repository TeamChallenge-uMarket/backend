package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.resetPassword.ConfiderCodeRequest;
import com.example.securityumarket.models.resetPassword.PasswordRequest;
import com.example.securityumarket.models.resetPassword.SenderCodeRequest;
import com.example.securityumarket.models.entities.AppUser;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailService {
    public MailService(PasswordEncoder passwordEncoder, AppUserDAO appUserDAO, JavaMailSender javaMailSender) {
        this.passwordEncoder = passwordEncoder;
        this.appUserDAO = appUserDAO;
        this.javaMailSender = javaMailSender;
    }

    private PasswordEncoder passwordEncoder;

    private AppUserDAO appUserDAO;

    private AppUser appUser;

    private String code;

    JavaMailSender javaMailSender;

    public ResponseEntity<String> sendCode(SenderCodeRequest senderCodeRequest) {
        if (appUserDAO.findAppUserByEmail(senderCodeRequest.getEmail()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email address you entered is not associated with any account.\n" +
                    "Please make sure you've entered the correct email address or sign up for a new account.\n");
        }
        appUser =  appUserDAO.findAppUserByEmail(senderCodeRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException(
                "no " + "such user"));

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        generateRandomCode();
        try {
            mimeMessage.setFrom(new InternetAddress("authenticator.umarket@gmail.com"));
            helper.setTo(senderCodeRequest.getEmail());
            helper.setSubject("Password Reset Verification");
            helper.setText("Your verification code: " + code, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
        return ResponseEntity.ok("A verification code for password reset has been sent to your registered email address.\n" +
                        "Please check your inbox and use the provided code to reset your password.\n");
    }

    public void generateRandomCode() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        code = String.valueOf(randomNumber);
    }

    public ResponseEntity<String> confirmResetCode(ConfiderCodeRequest confiderCodeRequest) {
        if (code.equals(confiderCodeRequest.getCodeConfirm())) {
            return ResponseEntity.ok("Code confirmed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code");
        }
    }


    public ResponseEntity<String> reset(PasswordRequest passwordRequest) {
        if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        } else {
            appUser.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
            appUserDAO.save(appUser);
            return ResponseEntity.ok("Password reset successfully");
        }
    }

}
