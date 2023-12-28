package com.example.securityumarket.util;

import com.example.securityumarket.dto.notification.NotificationRequest;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.models.Users;
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

    private final JavaMailSender javaMailSender;


    @Value("${mail.code.expiration.time}")
    public long codeExpirationTime;

    @Value("${vps.fronted.url}")
    private String frontendUrl;

    @Value("${mail.request.login-page.url}")
    private String LoginUrl;

    @Value("${mail.request.recover-password-page.url}")
    private String recoverPasswordUrl;

    @Value("${mail.sender.address}")
    private String senderEmail;

    @Value("${mail.sender.name}")
    private String senderName;


    @Async
    public void sendRegistrationEmail(String email, String token) throws MessagingException {
        String subject = "Verify account";
        String messageText = String.format(
                "<div><a href=\"%s%s?email=%s&token=%s\" target=\"_blank\">click link to verify</a></div>",
                frontendUrl + "/login", LoginUrl, email, token
        );

        MimeMessage mimeMessage = createAndConfigureMimeMessage(email, senderEmail, senderName, subject, messageText);
        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendResetPasswordEmail(String email, String token) throws MessagingException {
        String subject = "Reset password account";
        String messageText = String.format(
                "<div><a href=\"%s%s?email=%s&token=%s\" target=\"_blank\">click link to reset password</a></div>",
                frontendUrl, recoverPasswordUrl, email, token
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
            LocalDateTime now = LocalDateTime.now().minus(codeExpirationTime, ChronoUnit.MILLIS);
            return user.getRefreshToken().equals(token) && now.isBefore(lastUpdate);
        } else {
            throw new DataNotValidException("No recent updates for the user");
        }
    }

    @Async
    public void sendNotificationEmail(String email, String subject, String messageText) throws MessagingException {
        String senderEmail = "authenticator.umarket@gmail.com";
        String senderName = "uAuto";
        MimeMessage mimeMessage = createAndConfigureMimeMessage(email, senderEmail, senderName, subject, messageText);
        javaMailSender.send(mimeMessage);
    }

    public void sendSubscriptionNotification(NotificationRequest notification) {
        String notificationMessage = buildNotificationMessage(notification);
        try {
            sendNotificationEmail(notification.email(), notification.subscriptionName(), notificationMessage);
        } catch (MessagingException e) {
            throw new EmailSendingException();
        }
    }

    private String buildNotificationMessage(NotificationRequest request) {
        return String.format("За вашою підпискою %s додано новий автомобіль: %s!\n" +
                        "Переходь за посиланням щоб переглянути деталіше: %s/api/v1/transport/%s",
                request.subscriptionName(), request.transportDetail(), frontendUrl, request.transportId());
    }
}