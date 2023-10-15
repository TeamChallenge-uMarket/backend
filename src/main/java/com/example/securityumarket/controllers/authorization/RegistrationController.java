package com.example.securityumarket.controllers.authorization;


import com.example.securityumarket.models.DTO.login_page.RegisterRequest;
import com.example.securityumarket.models.authentication.AuthenticationResponse;
import com.example.securityumarket.services.authorization.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/v1/authorization/register")
@Tag(name = "Registration", description = "Registration page endpoints: registration, verify-account, resend-code")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(
            summary = "Get Registration Page",
            description = "This endpoint returns the registration page for the application."
    )
    @GetMapping("")
    public String getRegistrationPage() {
        return "register";
    }

    @Operation(
            summary = "Get Confirm Code Page",
            description = "This endpoint returns the confirmation code page for the application."
    )
    @GetMapping("/resend-code")
    public String getConfirmCodePage() {
        return "resend-code";
    }

    @Operation(
            summary = "User Registration",
            description = "This endpoint allows users to register for the application."
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict - Duplicate Data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Email Sending Exception", content = @Content)

    })
    @PostMapping("")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return registrationService.register(registerRequest);
    }

    @Operation(
            summary = "Resend Confirmation Code",
            description = "This endpoint allows users to resend the confirmation code to their email."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmation code resent successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Email Sending Exception", content = @Content)
    })
    @PutMapping("/resend-code")
    public ResponseEntity<String> resendCode(@RequestParam String email) {
        return registrationService.resendCode(email);
    }

    @Operation(
            summary = "Verify Account",
            description = "This endpoint allows users to verify their account using a confirmation token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account verified successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
                                                @RequestParam String token) {
        return registrationService.verifyAccount(email, token);
    }
}
