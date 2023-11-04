package com.example.securityumarket.services.authorization;

import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.models.DTO.login_page.RegisterRequest;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.services.security.JwtService;
import com.example.securityumarket.util.EmailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final JwtService jwtService;

    private final EmailUtil emailUtil;


    @Transactional
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest);
        Users user = buildUserFromRequest(registerRequest);
        return sendEmailAndSaveUser(user);
    }

    public ResponseEntity<String> resendCode(String email) {
        Users user = userService.findAppUserByEmail(email);
        if (user.isActive()) {
            throw new DataNotValidException("Account with this email: " + email + "has already activated. You can login.");
        } else {
            return sendEmailAndSaveUser(user);
        }
    }


    private ResponseEntity<String> sendEmailAndSaveUser(Users user) {
        String email = user.getEmail();
        String token = jwtService.generateRefreshToken(user);

        try {
            emailUtil.sendRegistrationEmail(email, token);
        } catch (MessagingException e) {
            throw new EmailSendingException();
        }

        user.setRefreshToken(token);
        userService.save(user);
        return ResponseEntity.ok("Email sent. Please verify account within 5 minutes");
    }

    public ResponseEntity<String> verifyAccount(String email, String token) {
        Users user = userService.findAppUserByEmail(email);
        if (emailUtil.verifyAccount(user, token)) {
            if (user.isActive()) {
                return ResponseEntity.ok("Your account is already active! You can login.");
            }
            user.setActive(true);
            userService.save(user);
            return ResponseEntity.ok("Account verified! You can login.");
        } else {
            return ResponseEntity.status(422).body("Token has expired. Please regenerate token and try again");
        }
    }


    private void validateRegisterRequest(RegisterRequest registerRequest) {
        userService.isUserEmailUnique(registerRequest.getEmail());
    }

    private Users buildUserFromRequest(RegisterRequest registerRequest) {
        return Users.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .active(false)
                .build();
    }
}
