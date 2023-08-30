package com.example.securityumarket.services;

import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.models.*;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.example.securityumarket.services.MailService.CODE_EXPIRATION_TIME_MS;
import static org.apache.logging.log4j.util.Strings.isBlank;


@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final UsersDAO usersDAO;
    private final JwtService jwtService;
    private final MailService mailService;

    private Users users;

    public RegistrationService(PasswordEncoder passwordEncoder, UsersDAO usersDAO, JwtService jwtService, MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.usersDAO = usersDAO;
        this.jwtService = jwtService;
        this.mailService = mailService;
    }

    @Transactional
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        ResponseEntity<String> validationResponse = validateRegisterRequest(registerRequest);
        if (validationResponse != null) {
            return validationResponse;
        }

        users = buildUserFromRequest(registerRequest);
        jwtService.generateToken(users);
        String refreshToken = jwtService.generateRefreshToken(users);
        users.setRefreshToken(refreshToken);

        mailService.sendCode(users.getEmail());

        return ResponseEntity.ok("Verification code sent successfully");
    }

    public ResponseEntity<String> confirmRegistration(String codeConfirm) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mailService.getCodeCreationTime();
        if (elapsedTime > CODE_EXPIRATION_TIME_MS) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Code has expired");
        }
        if (mailService.getVerificationCode().equals(codeConfirm)) {
            usersDAO.save(users);
            return ResponseEntity.ok("Code confirmed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Invalid code");
        }
    }

    private Users buildUserFromRequest(RegisterRequest registerRequest) {
        return Users.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(normalizePhoneNumber(registerRequest.getPhone()))
                .country(registerRequest.getAddress().getCountry())
                .city(registerRequest.getAddress().getCity())
//                .role(Role.USER)
                .build();
    }



    private String normalizePhoneNumber(String inputPhoneNumber) {
        String digitsAndParentheses = inputPhoneNumber.replaceAll("[^\\d()]", "");

        String digitsOnly = digitsAndParentheses.replaceAll("[()]", "");

        String normalizedNumber;
        if (digitsOnly.startsWith("38")) {
            normalizedNumber = "+" + digitsOnly;
        } else {
            normalizedNumber = "+38" + digitsOnly;
        }

        return normalizedNumber;
    }


    private ResponseEntity<String> validateRegisterRequest(RegisterRequest registerRequest) {
        if (isBlank(registerRequest.getName())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("User name is required");
        }

        if (isBlank(registerRequest.getEmail()) || !registerRequest.getEmail().matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Invalid email");
        }

        if (usersDAO.findAppUserByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
        }

        if (isBlank(registerRequest.getPassword()) ||
                !registerRequest.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Password must be at least 8 characters long and contain at least one letter and one digit");
        }


        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Passwords do not match");
        }
        if (!isBlank(registerRequest.getPhone())) {
            if (!registerRequest.getPhone().matches("((\\+38)?\\(?\\d{3}\\)?[\\s.-]?(\\d{7}|\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}|\\d{3}-\\d{4}))")) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Invalid phone number");
            }
        }
        if (usersDAO.findAppUserByPhone(normalizePhoneNumber(registerRequest.getPhone())).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this phone already exists");
        }
        return null; // Всі поля валідні
    }
}
