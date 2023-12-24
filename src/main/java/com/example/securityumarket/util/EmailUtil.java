package com.example.securityumarket.util;

import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.models.entities.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${mail.code.expiration.time}")
    public long CODE_EXPIRATION_TIME_MS;

    @Value("${mail.base.url}")
    private String BASE_URL;

    @Value("${mail.request.login-page.url}")
    private String REQUEST_LOGIN_MAPPING_URL;

    @Value("${mail.request.recover-password-page.url}")
    private String REQUEST_RECOVER_PASSWORD_MAPPING_URL;


    private final JavaMailSender javaMailSender;


    @Async
    public void sendRegistrationEmail(String email, String token) throws MessagingException {
        String senderEmail = "authenticator.umarket@gmail.com";
        String senderName = "uAuto";
        String subject = "Verify account";
        String messageText = String.format(
                "<div><a href=\"%s%s?email=%s&token=%s\" target=\"_blank\">click link to verify</a></div>",
                BASE_URL+"/login", REQUEST_LOGIN_MAPPING_URL, email, token
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
                "<div><a href=\"%s%s?email=%s&token=%s\" target=\"_blank\">click link to reset password</a></div>",
                BASE_URL,REQUEST_RECOVER_PASSWORD_MAPPING_URL, email, token
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
            return user.getRefreshToken().equals(token) && now.isBefore(lastUpdate);
        } else {
            throw new DataNotValidException("No recent updates for the user");
        }
    }

    public void sendSubscriptionNotify(Users user, Subscription subscription) {
        //TODO
    }
}