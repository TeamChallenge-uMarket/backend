package com.example.securityumarket.services.authorization;

import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.models.DTO.login_page.PasswordRequest;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.util.EmailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ResetPasswordService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final EmailUtil emailUtil;

    private final JwtService jwtService;


    public ResponseEntity<String> sendResetPasswordCode(String email) {
        Users user = userService.findAppUserByEmail(email);
        String token = jwtService.generateToken(user);
        try {
            emailUtil.sendResetPasswordEmail(email, token);
        } catch (MessagingException e) {
            throw new EmailSendingException();
        }
        user.setRefreshToken(token);
        userService.save(user);
        return ResponseEntity.ok("Verification code sent successfully. Check your email");
    }

    public ResponseEntity<String> verifyAccount(String email, String token) {
        Users user = userService.findAppUserByEmail(email);
        if (emailUtil.verifyAccount(user, token)) {
            return ResponseEntity.ok("redirect:/api/v1/authorization/reset-password/form");
        } else
            return ResponseEntity.status(422).body("Token has expired. Please regenerate token and try again");
    }

    public ResponseEntity<String> resetPassword(PasswordRequest passwordRequest) {
        Users user = userService.findAppUserByEmail(passwordRequest.getEmail());
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userService.save(user);
        return ResponseEntity.ok("Password reset successfully");
    }
}