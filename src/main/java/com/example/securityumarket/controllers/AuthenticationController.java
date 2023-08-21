package com.example.securityumarket.controllers;

import com.example.securityumarket.models.authentication.AuthenticationRequest;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.RefreshRequest;
import com.example.securityumarket.models.RegisterRequest;
import com.example.securityumarket.models.resetPassword.ConfiderCodeRequest;
import com.example.securityumarket.models.resetPassword.PasswordRequest;
import com.example.securityumarket.models.resetPassword.SenderCodeRequest;
import com.example.securityumarket.services.LoginService;
import com.example.securityumarket.services.RegistrationService;
import com.example.securityumarket.services.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Validated
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private RegistrationService registrationService;
    private LoginService loginService;
    private MailService mailService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return registrationService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(loginService.login(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh (@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(loginService.refresh(refreshRequest));
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode (@RequestBody SenderCodeRequest senderCodeRequest) {
        return mailService.sendCode(senderCodeRequest);
    }

    @PostMapping("/confirm-reset-code")
    public ResponseEntity<String> confirmResetCode(@RequestBody ConfiderCodeRequest code) {
        return mailService.confirmResetCode(code);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword (@RequestBody PasswordRequest passwordRequest) {
        return mailService.reset(passwordRequest);
    }
}
