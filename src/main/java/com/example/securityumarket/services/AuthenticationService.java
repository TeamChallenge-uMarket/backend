package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.*;
import com.example.securityumarket.models.entities.AppUser;
import com.example.securityumarket.models.entities.Role;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthenticationService {
    private PasswordEncoder passwordEncoder;
    private AppUserDAO appUserDAO;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    private ResponseEntity<String> validateRegisterRequest(RegisterRequest registerRequest) {
        if (StringUtils.isBlank(registerRequest.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name is required");
        }

        if (StringUtils.isBlank(registerRequest.getEmail()) || !registerRequest.getEmail().matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email");
        }

        if (StringUtils.isBlank(registerRequest.getPassword()) ||
                !registerRequest.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password must be at least 8 characters long and contain at least one letter and one digit");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }
        if (registerRequest.getPhone()!=null && !registerRequest.getPhone().matches("(?=.*\\+[0-9]{3}\\s?[0-9]{2}\\s?[0-9]{3}\\s?[0-9]{4,5}$)")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number");
        }
        return null; // Всі поля валідні
    }


    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        ResponseEntity<String> validationResponse = validateRegisterRequest(registerRequest);
        if (validationResponse != null) {
            return validationResponse;
        }
        AppUser appUser = AppUser.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .country(registerRequest.getAddress().getCountry())
                .city(registerRequest.getAddress().getCity())
                .role(Role.USER)
                .build();
        String jwtToken = jwtService.generateToken(appUser);

        String refreshToken = jwtService.generateRefreshToken(appUser);
        appUser.setRefreshToken(refreshToken);

        appUserDAO.save(appUser);

        AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.ok("AppUser is registered");
    }


//    private Optional<String> validation(RegisterRequest registerRequest) {
//        if (registerRequest.getName().isBlank()) {
//            return Optional.of("Name is required");
//        }
//        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
//            return Optional.of("Password is not correct");
//        }
//        if (!registerRequest.getPhone().isBlank()) {
//            if (appUserDAO.existsByPhone(registerRequest.getPhone())) {
//                return Optional.of("Phone number is already registered");
//            }
//        }
//        return Optional.empty();
//
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        AppUser appUser = authenticate(authenticationRequest);

        String jwtToken = jwtService.generateToken(appUser);
        String refreshToken = jwtService.generateRefreshToken(appUser);
        appUser.setRefreshToken(refreshToken);
        appUserDAO.save(appUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    protected AppUser authenticate (AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        return appUserDAO.findAppUserByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException(
                "no " + "such user and no authenticate"));
    }

    public AuthenticationResponse refresh(RefreshRequest refreshRequest) {
        String token = refreshRequest.getRefreshToken();
        String username = jwtService.extractUsername(token);
        AppUser appUser = appUserDAO
                .findAppUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found by refresh token"));
        String newAccessToken = null;
        String newRefreshToken = null;
        if (appUser.getRefreshToken().equals(token)) {
            newAccessToken = jwtService.generateToken(appUser);
            newRefreshToken = jwtService.generateRefreshToken(appUser);
            appUser.setRefreshToken(newRefreshToken);
            appUserDAO.save(appUser);
        }
        return AuthenticationResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
