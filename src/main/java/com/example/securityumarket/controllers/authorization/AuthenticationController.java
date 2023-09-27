package com.example.securityumarket.controllers.authorization;

import com.example.securityumarket.models.authentication.AuthenticationRequest;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.models.RegisterRequest;
import com.example.securityumarket.services.authorization.LoginService;
import com.example.securityumarket.services.authorization.RegistrationService;
import com.example.securityumarket.services.security.TokenRefreshService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/v1/authorization")
public class AuthenticationController {

    private RegistrationService registrationService;
    private LoginService loginService;
    private TokenRefreshService tokenRefreshService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "register";
    }

    @GetMapping("/register/confirm-code")
    public String getConfirmCodePage() {
        return "register/confirm-code";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return registrationService.register(registerRequest);
    }

    @PostMapping("/register/confirm-code")
    public ResponseEntity<String> confirmCode(@RequestBody Map<String, String> requestConfirmCode) {
        return registrationService.confirmRegistration(requestConfirmCode.get("confirmCode"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(loginService.login(authenticationRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody Map<String, String> refreshRequest) {
        return ResponseEntity.ok(tokenRefreshService.refreshTokens(refreshRequest.get("refreshToken")));
    }

}
