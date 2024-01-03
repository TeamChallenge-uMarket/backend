package com.example.securityumarket.services.pages;

import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.dto.pages.login.PasswordRequest;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.util.EmailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResetPasswordPageService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final EmailUtil emailUtil;

    private final JwtService jwtService;


    public void sendResetPasswordCode(String email) {
        Users user = userService.findAppUserByEmail(email);
        String token = jwtService.generateToken(user);
        try {
            emailUtil.sendResetPasswordEmail(email, token);
        } catch (MessagingException e) {
            throw new EmailSendingException();
        }
        user.setRefreshToken(token);
        userService.save(user);

        log.info("Reset password code sent to user with email: {}", email);
    }

    public void verifyAccount(String email, String token) {
        Users user = userService.findAppUserByEmail(email);
        if (!emailUtil.verifyAccount(user, token)) {
            throw new DataNotValidException
                    ("Token has expired. Please regenerate token and try again");
        }

        log.info("User with email {} verified account for password reset.", email);
    }

    public void resetPassword(PasswordRequest passwordRequest) {
        Users user = userService.findAppUserByEmail(passwordRequest.getEmail());
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userService.save(user);

        log.info("Password reset successfully for user with email: {}", passwordRequest.getEmail());
    }
}