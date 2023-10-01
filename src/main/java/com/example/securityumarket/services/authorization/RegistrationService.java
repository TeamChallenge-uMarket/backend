package com.example.securityumarket.services.authorization;

import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.DTO.login_page.RegisterRequest;
import com.example.securityumarket.models.entities.City;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.CityService;
import com.example.securityumarket.services.jpa.RegionService;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.services.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.securityumarket.services.authorization.MailService.CODE_EXPIRATION_TIME_MS;


@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final RegionService regionService;

    private final CityService cityService;

    private final JwtService jwtService;

    private final MailService mailService;

    private Users users;


    public RegistrationService(PasswordEncoder passwordEncoder, UserService userService, RegionService regionService, CityService cityService, JwtService jwtService, MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.regionService = regionService;
        this.cityService = cityService;
        this.jwtService = jwtService;
        this.mailService = mailService;
    }

    @Transactional
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest);

        users = buildUserFromRequest(registerRequest);
        jwtService.generateToken(users);
        String refreshToken = jwtService.generateRefreshToken(users);
        users.setRefreshToken(refreshToken);

        mailService.sendCode(users.getEmail());

        return ResponseEntity.ok("Verification code sent successfully");
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

    public ResponseEntity<String> confirmRegistration(String codeConfirm) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mailService.getCodeCreationTime();
        if (elapsedTime > CODE_EXPIRATION_TIME_MS) {
            return ResponseEntity.status(HttpStatus.GONE).body("Code has expired");
        }
        if (mailService.getVerificationCode().equals(codeConfirm)) {
            userService.save(users);
            return ResponseEntity.ok("Code confirmed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Invalid code");
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
                .build();
    }
}
