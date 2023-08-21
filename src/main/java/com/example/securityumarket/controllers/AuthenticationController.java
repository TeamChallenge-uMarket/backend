package com.example.securityumarket.controllers;

import com.example.securityumarket.models.authentication.AuthenticationRequest;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.RegisterRequest;
import com.example.securityumarket.services.JwtRefreshService;
import com.example.securityumarket.services.LoginService;
import com.example.securityumarket.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private RegistrationService registrationService;
    private LoginService loginService;
    private JwtRefreshService jwtRefreshService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return registrationService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(loginService.login(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody Map<String, String> refreshRequest) {
        return ResponseEntity.ok(jwtRefreshService.refresh(refreshRequest.get("refreshToken")));
    }

}
