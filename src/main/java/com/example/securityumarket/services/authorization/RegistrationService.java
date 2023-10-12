package com.example.securityumarket.services.authorization;

import com.example.securityumarket.exception.EmailSendingException;
import com.example.securityumarket.models.DTO.login_page.RegisterRequest;
import com.example.securityumarket.models.entities.City;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.CityService;
import com.example.securityumarket.services.jpa.RegionService;
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

    private final RegionService regionService;

    private final CityService cityService;

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
        return sendEmailAndSaveUser(user);
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
        String normalizePhoneNumber = normalizePhoneNumber(registerRequest.getPhone());
        userService.isUserPhoneUnique(normalizePhoneNumber);
        getAddressFromRequest(registerRequest);
    }

    private City getAddressFromRequest(RegisterRequest registerRequest) {
        if (registerRequest.getAddressDTO().isEmpty()) {
            return null;
        } else {
            String region = registerRequest.getAddressDTO().getRegion();
            String city = registerRequest.getAddressDTO().getCity();

            regionService.findByDescription(region);
            cityService.findByDescription(city);

            return cityService.findByRegionDescriptionAndDescription(region, city);
        }
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

    private Users buildUserFromRequest(RegisterRequest registerRequest) {
        return Users.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(normalizePhoneNumber(registerRequest.getPhone()))
                .city(getAddressFromRequest(registerRequest))
                .active(false)
                .build();
    }
}
