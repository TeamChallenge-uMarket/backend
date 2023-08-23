package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.*;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.entities.AppUser;
import com.example.securityumarket.models.entities.Role;
import com.example.securityumarket.services.JwtService;
import com.example.securityumarket.services.MailService;
import com.example.securityumarket.services.UserCleanupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.isBlank;


@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserDAO appUserDAO;
    private final JwtService jwtService;
    private final MailService mailService;
    private final UserCleanupService cleanupService;

    public RegistrationService(PasswordEncoder passwordEncoder, AppUserDAO appUserDAO, JwtService jwtService, MailService mailService, UserCleanupService cleanupService) {
        this.passwordEncoder = passwordEncoder;
        this.appUserDAO = appUserDAO;
        this.jwtService = jwtService;
        this.mailService = mailService;
        this.cleanupService = cleanupService;
    }

    @Transactional
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        ResponseEntity<String> validationResponse = validateRegisterRequest(registerRequest);
        if (validationResponse != null) {
            return validationResponse;
        }

        AppUser appUser = buildAppUserFromRequest(registerRequest);
        String jwtToken = jwtService.generateToken(appUser);
        String refreshToken = jwtService.generateRefreshToken(appUser);
        appUser.setRefreshToken(refreshToken);
        appUserDAO.save(appUser);

        mailService.sendCode(appUser.getEmail());

        cleanupService.scheduleCleanupUnconfirmedUsers();
        return ResponseEntity.ok("Verification code sent successfully");
    }

    public ResponseEntity<String> confirmRegistration(String codeConfirm) {
        if (mailService.verificationCode.equals(codeConfirm)) {
            mailService.verificationCode = null; // код використано, перевстановлюємо його на null
            Optional<AppUser> appUserByEmail = appUserDAO.findAppUserByEmail(mailService.userEmail);
            appUserByEmail.get().setConfirmEmail(true);
            appUserDAO.save(appUserByEmail.get());
            return ResponseEntity.ok("Code confirmed successfully. AppUser is registered");
        } else {
            Optional<AppUser> appUserByEmail = appUserDAO.findAppUserByEmail(mailService.userEmail);
            appUserByEmail.ifPresent(appUser -> appUserDAO.deleteById(appUser.getId()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code");
        }
    }
    private AppUser buildAppUserFromRequest(RegisterRequest registerRequest) {
        return AppUser.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(normalizePhoneNumber(registerRequest.getPhone()))
                .country(registerRequest.getAddress().getCountry())
                .city(registerRequest.getAddress().getCity())
                .role(Role.USER)
                .build();
    }



    public String normalizePhoneNumber(String inputPhoneNumber) {
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name is required");
        }

        if (isBlank(registerRequest.getEmail()) || !registerRequest.getEmail().matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email");
        }

        if (appUserDAO.findAppUserByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");
        }

        if (isBlank(registerRequest.getPassword()) ||
                !registerRequest.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must be at least 8 characters long and contain at least one letter and one digit");
        }


        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }
        if (!isBlank(registerRequest.getPhone())) {
            if (!registerRequest.getPhone().matches("((\\+38)?\\(?\\d{3}\\)?[\\s.-]?(\\d{7}|\\d{3}[\\s.-]\\d{2}[\\s.-]\\d{2}|\\d{3}-\\d{4}))")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number");
            }
        }
        if (appUserDAO.findAppUserByPhone(normalizePhoneNumber(registerRequest.getPhone())).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this phone already exists");
        }
        return null; // Всі поля валідні
    }
}
