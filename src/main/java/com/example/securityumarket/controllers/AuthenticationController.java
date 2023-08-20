package com.example.securityumarket.controllers;

import com.example.securityumarket.models.*;
import com.example.securityumarket.models.resetPassword.ConfiderCodeRequest;
import com.example.securityumarket.models.resetPassword.PasswordRequest;
import com.example.securityumarket.models.resetPassword.SenderCodeRequest;
import com.example.securityumarket.services.AuthenticationService;
import com.example.securityumarket.services.MailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Validated
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;
    private MailService mailService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(AuthenticationResponse.builder().build(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh (@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(authenticationService.refresh(refreshRequest));
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
