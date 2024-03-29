package com.example.securityumarket.controllers.pages;

import com.example.securityumarket.dto.authentication.AuthenticationRequest;
import com.example.securityumarket.dto.authentication.AuthenticationResponse;
import com.example.securityumarket.dto.pages.login.OAuth2Request;
import com.example.securityumarket.services.pages.LoginPageService;
import com.example.securityumarket.services.security.TokenRefreshService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/authorization/login")
@Tag(name = "Login", description = "This controller contains login page endpoints, such as: login, refresh-token")
public class LoginController {

    private final LoginPageService loginPageService;

    private final TokenRefreshService tokenRefreshService;


    @Operation(
            summary = "User Login",
            description = "This endpoint allows users to log in and obtain an authentication token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "422",
                    description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Conflict - Duplicate Data", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Not Found - Data Not Found", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error - Email Sending Exception", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> login(
            @Parameter(
                    description = "The login request sent by user in order to obtain " +
                    "an authentication token. It contains the necessary credentials to perform authentication")
            @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(loginPageService.login(authenticationRequest));
    }

    @PostMapping("/oauth2")
    public ResponseEntity<AuthenticationResponse> loginOAuth2(
            @RequestBody OAuth2Request oAuth2Request) {
        return ResponseEntity.ok(loginPageService.loginOAuth2(oAuth2Request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody Map<String, String> refreshRequest) {
        return ResponseEntity.ok(tokenRefreshService.refreshTokens(refreshRequest.get("refreshToken")));
    }
}
