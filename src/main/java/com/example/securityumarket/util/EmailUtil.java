package com.example.securityumarket.util;

import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.models.entities.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@RequiredArgsConstructor
@Component
public class EmailUtil {

    public static final long CODE_EXPIRATION_TIME_MS = 5 * 60 * 1000; // 5 minutes
    private static final String BASE_URL = "http://localhost:8080";
    private static final String REQUEST_MAPPING_URL = "/api/v1/authorization";


    private final JavaMailSender javaMailSender;


    @Async
    public void sendRegistrationEmail(String email, String token) throws MessagingException {
        String senderEmail = "authenticator.umarket@gmail.com";
        String senderName = "uAuto";
        String subject = "Verify account";
        String messageText = String.format(
                "<div><a href=\"%s%s/register/verify-account?email=%s&token=%s\" target=\"_blank\">click link to verify</a></div>",
                BASE_URL,REQUEST_MAPPING_URL, email, token
        );

        MimeMessage mimeMessage = createAndConfigureMimeMessage(email, senderEmail, senderName, subject, messageText);
        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendResetPasswordEmail(String email, String token) throws MessagingException {
        String senderEmail = "authenticator.umarket@gmail.com";
        String senderName = "uAuto";
        String subject = "Reset password account";
        String messageText = String.format(
                "<div><a href=\"%s%s/reset-password?email=%s&token=%s\" target=\"_blank\">click link to reset password</a></div>",
                BASE_URL,REQUEST_MAPPING_URL, email, token
        );

        MimeMessage mimeMessage = createAndConfigureMimeMessage(email, senderEmail, senderName, subject, messageText);
        javaMailSender.send(mimeMessage);
    }


    private MimeMessage createAndConfigureMimeMessage(String email, String senderEmail, String senderName, String subject, String messageText) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessage.setFrom(new InternetAddress(senderEmail, senderName));
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(messageText, true);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailSendingException();
        }
        return mimeMessage;
    }

    
    public boolean verifyAccount(Users user, String token) {
        LocalDateTime lastUpdate = user.getLastUpdate();
        if (lastUpdate != null) {
            LocalDateTime now = LocalDateTime.now().minus(CODE_EXPIRATION_TIME_MS, ChronoUnit.MILLIS);
            return user.getRefreshToken().equals(token) &&   now.isBefore(lastUpdate);
        } else {
            throw new DataNotValidException("No recent updates for the user");
        }
    }
}

