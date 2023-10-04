package com.example.securityumarket.services.authorization;
import com.example.securityumarket.models.DTO.login_page.PasswordRequest;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.securityumarket.services.authorization.MailService.CODE_EXPIRATION_TIME_MS;

@RequiredArgsConstructor
@Service
public class ResetPasswordService {
    
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;


    public ResponseEntity<String> sendResetPasswordCode(String email) {
        userService.findAppUserByEmail(email);
        mailService.sendCode(email);
        return ResponseEntity.ok("Verification code sent successfully");
    }

    public ResponseEntity<String> confirmResetPasswordCode(String codeConfirm) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mailService.getCodeCreationTime();
        if (elapsedTime > CODE_EXPIRATION_TIME_MS) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Code has expired");
        }
        if (mailService.getVerificationCode().equals(codeConfirm)) {
            return ResponseEntity.ok("Code confirmed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Invalid code");
        }
    }

    public ResponseEntity<String> resetPassword(PasswordRequest passwordRequest) {
        Users user = userService.findAppUserByEmail(mailService.getUserEmail());
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userService.save(user);
        return ResponseEntity.ok("Password reset successfully");
    }
}
