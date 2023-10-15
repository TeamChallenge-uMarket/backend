package com.example.securityumarket.controllers.authorization;

import com.example.securityumarket.models.authentication.AuthenticationRequest;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.services.authorization.LoginService;
import com.example.securityumarket.services.security.TokenRefreshService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get Login Page",
            description = "This endpoint returns the login page for the application."
    )
    @GetMapping("")
    public String getLoginPage() {
        return "login";
    }

    @Operation(
            summary = "User Login",
            description = "This endpoint allows users to log in and obtain an authentication token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict - Duplicate Data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient Permissions", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Email Sending Exception", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(loginService.login(authenticationRequest));
    }

    @Operation(
            summary = "Refresh Token",
            description = "This is a test endpoint for token refreshing. It allows users to refresh their authentication tokens using a refresh token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
    })
    @PostMapping("/refresh")  //TEST METHOD
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody Map<String, String> refreshRequest) {
        return ResponseEntity.ok(tokenRefreshService.refreshTokens(refreshRequest.get("refreshToken")));
    }

}
