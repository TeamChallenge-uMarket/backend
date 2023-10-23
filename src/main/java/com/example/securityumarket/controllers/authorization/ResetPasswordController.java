package com.example.securityumarket.controllers.authorization;

import com.example.securityumarket.models.DTO.login_page.PasswordRequest;
import com.example.securityumarket.services.authorization.ResetPasswordService;
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
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/v1/authorization/reset-password")
@Tag(name = "Reset passwords", description = "This controller contains the following endpoints: reset password endpoints: reset-password, form, send-code," +
        " resend-code, verify account")
public class ResetPasswordController {
    private ResetPasswordService resetPasswordService;

    @Operation(
            summary = "Get Reset Password Page",
            description = "This endpoint returns the reset password page for the application."
    )
    @GetMapping("/page")
    public String getResetPasswordPage() {
        return "reset-password";
    }

    @Operation(
            summary = "Get Reset Password Form Page",
            description = "This endpoint returns the reset password form page for the application."
    )
    @GetMapping("/form")
    public String getConfirmCodePage() {
        return "reset-password form";
    }

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
        return resetPasswordService.sendResetPasswordCode(email);
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
        return resetPasswordService.verifyAccount(email, token);
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
        return resetPasswordService.resetPassword(passwordRequest);
    }
}
