package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.PasswordRequest;
import com.example.securityumarket.models.entities.AppUser;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MailService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserDAO appUserDAO;
    private final JavaMailSender javaMailSender;

    private String verificationCode;
    private String userEmail;


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


//@Service
//@AllArgsConstructor
//public class MailService {
//    public MailService(PasswordEncoder passwordEncoder, AppUserDAO appUserDAO, JavaMailSender javaMailSender) {
//        this.passwordEncoder = passwordEncoder;
//        this.appUserDAO = appUserDAO;
//        this.javaMailSender = javaMailSender;
//    }
//
//    private PasswordEncoder passwordEncoder;
//
//    private AppUserDAO appUserDAO;
//
//    private AppUser appUser;
//
//    private String code;
//
//    JavaMailSender javaMailSender;
//
//    public ResponseEntity<String> sendCode(String email) {
//        if (appUserDAO.findAppUserByEmail(email).isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("""
//                    The email address you entered is not associated with any account.
//                    Please make sure you've entered the correct email address or sign up for a new account.
//                    """);
//        }
//        appUser =  appUserDAO.findAppUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
//                "no " + "such user"));
//
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
//        generateRandomCode();
//        try {
//            String senderEmail = "authenticator.umarket@gmail.com";
//            String senderName = "uMarket";
//            mimeMessage.setFrom(new InternetAddress(senderEmail,senderName));
//            helper.setTo(email);
//            helper.setSubject("Password Reset Verification");
//            helper.setText("Your verification code: " + code, false);
//        } catch (MessagingException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        javaMailSender.send(mimeMessage);
//        return ResponseEntity.ok("""
//                A verification code for password reset has been sent to your registered email address.
//                Please check your inbox and use the provided code to reset your password.
//                """);
//    }
//
//    public void generateRandomCode() {
//        Random random = new Random();
//        int randomNumber = 100000 + random.nextInt(900000);
//        code = String.valueOf(randomNumber);
//    }
//
//    public ResponseEntity<String> confirmResetCode(String codeConfirm) {
//        if (code.equals(codeConfirm)) {
//            return ResponseEntity.ok("Code confirmed successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code");
//        }
//    }
//
//
//    public ResponseEntity<String> reset(PasswordRequest passwordRequest) {
//        if (!passwordRequest.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Password must be at least 8 characters long and contain at least one letter and one digit");
//        }
//        if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
//        } else {
//            appUser.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
//            appUserDAO.save(appUser);
//            return ResponseEntity.ok("Password reset successfully");
//        }
//    }
//
//}
