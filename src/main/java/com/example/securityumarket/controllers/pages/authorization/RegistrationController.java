package com.example.securityumarket.controllers.pages.authorization;


import com.example.securityumarket.models.DTO.pages.login.RegisterRequest;
import com.example.securityumarket.services.pages.RegistrationPageService;
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
@RequestMapping("/api/v1/authorization/register")
@Tag(name = "Registration", description = "This controller contains registration page endpoints, such as: registration, verify-account, resend-code")
public class RegistrationController {

    private final RegistrationPageService registrationPageService;


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
    public ResponseEntity<String> register(@RequestBody
                                               @Valid
                                               @Parameter(description = "The request provided by the user to perform a registration",
                                                       examples = @ExampleObject(value = "{" +
                                                               "\"name\": \"JohnDoe\"" +
                                                               "\"email\": \"test@gmail.com\"" +
                                                               "\"password\": \"Password11\"" +
                                                               "\"confirmPassword\": \"Password11\"" +
                                                               "}"))
                                               RegisterRequest registerRequest) {
        registrationPageService.register(registerRequest);
        return ResponseEntity.ok("Email sent. Please verify account within 5 minutes");
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
    public ResponseEntity<String> resendCode(
            @Parameter(description = "The email address of the user")
            @RequestParam String email) {
        registrationPageService.resendCode(email);
        return ResponseEntity.ok("Email with resend-code sent.");
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
    @GetMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@Parameter(description = "The email of the user")
                                                    @RequestParam String email,
                                                @Parameter(description = "The confirmation token")
                                                @RequestParam String token) {
        registrationPageService.verifyAccount(email, token);
        return ResponseEntity.ok("Account verified! You can login.");
    }
}
