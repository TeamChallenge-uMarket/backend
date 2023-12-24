package com.example.securityumarket.services.pages;

import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.models.DTO.pages.login.PasswordRequest;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.util.EmailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
    }

    public void verifyAccount(String email, String token) {
        Users user = userService.findAppUserByEmail(email);
        if (!emailUtil.verifyAccount(user, token)) {
            throw new DataNotValidException("Token has expired. Please regenerate token and try again");
        }
    }

    public void resetPassword(PasswordRequest passwordRequest) {
        Users user = userService.findAppUserByEmail(passwordRequest.getEmail());
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userService.save(user);
    }
}