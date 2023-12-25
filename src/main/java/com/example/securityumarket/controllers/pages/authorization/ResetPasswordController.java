package com.example.securityumarket.controllers.pages.authorization;

import com.example.securityumarket.dto.pages.login.PasswordRequest;
import com.example.securityumarket.services.pages.ResetPasswordPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/authorization/reset-password")
@Tag(name = "Reset passwords", description = "This controller contains the following endpoints: reset password endpoints: reset-password, form, send-code," +
        " resend-code, verify account")
public class ResetPasswordController {

    private ResetPasswordPageService resetPasswordPageService;

    @Operation(
            summary = "Send Reset Password Code",
            description = "This endpoint allows users to send a reset password code to their email."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Code sent successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found - Data Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Email Sending Exception", content = @Content)
    })
    @PutMapping("/send-code")
    public ResponseEntity<String> sendCode(@Parameter(description = "The email of the user") @RequestParam String email) {
        resetPasswordPageService.sendResetPasswordCode(email);
        return ResponseEntity.ok("Verification code sent successfully. Check your email");
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
    @GetMapping("")
    public ResponseEntity<String> verifyAccount(@Parameter(description = "The email of the user") @RequestParam String email,
                                                @Parameter(description = "The confirmation token")
                                                @RequestParam String token) {
        resetPasswordPageService.verifyAccount(email, token);
        return ResponseEntity.ok("redirect:/api/v1/authorization/reset-password/form");

    }

    @Operation(
            summary = "Reset Password",
            description = "This endpoint allows users to reset their password."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content)
    })
    @PostMapping("")
    public ResponseEntity<String> resetPassword(@Parameter(description = "The request for resetting the user password," +
            " which contains the necessary credentials", examples = @ExampleObject
            (value = "{\"email\": \"john_doe@gmail.com\", \"password\": \"NewPassword11\", confirmPassword: \"NewPassword11\"}"))
                                                @RequestBody @Valid PasswordRequest passwordRequest) {
        resetPasswordPageService.resetPassword(passwordRequest);
        return ResponseEntity.ok("Password reset successfully");
    }
}
