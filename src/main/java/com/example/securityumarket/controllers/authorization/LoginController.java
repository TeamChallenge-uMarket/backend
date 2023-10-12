package com.example.securityumarket.controllers.authorization;

import com.example.securityumarket.models.authentication.AuthenticationRequest;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.services.authorization.LoginService;
import com.example.securityumarket.services.security.TokenRefreshService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/v1/authorization/login")
@Tag(name = "Login", description = "Login page endpoints: login, refresh-token")
public class LoginController {

    private final LoginService loginService;

    private final TokenRefreshService tokenRefreshService; //TEST VARIABLE


    @GetMapping("")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(loginService.login(authenticationRequest));
    }

    @PostMapping("/refresh")  //TEST METHOD
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody Map<String, String> refreshRequest) {
        return ResponseEntity.ok(tokenRefreshService.refreshTokens(refreshRequest.get("refreshToken")));
    }

}
