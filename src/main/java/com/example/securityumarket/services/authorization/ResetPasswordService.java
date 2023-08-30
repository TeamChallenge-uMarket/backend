package com.example.securityumarket.services.authorization;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.models.PasswordRequest;
import com.example.securityumarket.models.entities.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.securityumarket.services.authorization.MailService.CODE_EXPIRATION_TIME_MS;

@Service
public class ResetPasswordService {
    
    private final UsersDAO usersDAO;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;



    public ResetPasswordService(UsersDAO usersDAO, PasswordEncoder passwordEncoder, MailService mailService) {
        this.usersDAO = usersDAO;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public ResponseEntity<String> sendResetPasswordCode(String email) {
        Optional<Users> optionalAppUser = usersDAO.findAppUserByEmail(email);
        if (optionalAppUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The email address you entered is not associated with any account.");
        }
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
        if (!isValidPassword(passwordRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must be at least 8 characters long and contain at least one letter and one digit");
        }

        if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }

        Optional<Users> optionalAppUser = usersDAO.findAppUserByEmail(mailService.getUserEmail());
        Users users = optionalAppUser.orElseThrow(() -> new UsernameNotFoundException("No user with the given email"));

        users.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        usersDAO.save(users);


        return ResponseEntity.ok("Password reset successfully");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
}
