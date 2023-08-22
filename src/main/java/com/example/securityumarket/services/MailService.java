package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.PasswordRequest;
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

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

@Service
public class MailService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserDAO appUserDAO;
    private final JavaMailSender javaMailSender;

    protected String verificationCode;
    protected String userEmail;


    public MailService(PasswordEncoder passwordEncoder, AppUserDAO appUserDAO, JavaMailSender javaMailSender) {
        this.passwordEncoder = passwordEncoder;
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
        sendVerificationEmail(verificationCode);

        return ResponseEntity.ok("Verification code sent successfully");
    }

    public ResponseEntity<String> confirmResetCode(String codeConfirm) {
        if (verificationCode.equals(codeConfirm)) { // Порівнюємо збережений код з введеним користувачем
            return ResponseEntity.ok("Code confirmed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code");
        }
    }

    public ResponseEntity<String> resetPassword(PasswordRequest passwordRequest) {
        if (!isValidPassword(passwordRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must be at least 8 characters long and contain at least one letter and one digit");
        }

        if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }

        Optional<AppUser> optionalAppUser = appUserDAO.findAppUserByEmail(userEmail);
        AppUser appUser = optionalAppUser.orElseThrow(() -> new UsernameNotFoundException("No user with the given email"));

        appUser.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        appUserDAO.save(appUser);


        return ResponseEntity.ok("Password reset successfully");
    }

    private void sendVerificationEmail(String code) {
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

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
}