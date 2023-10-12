package com.example.securityumarket.controllers.authorization;


import com.example.securityumarket.models.DTO.login_page.RegisterRequest;
import com.example.securityumarket.services.authorization.RegistrationService;
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


    @GetMapping("")
    public String getRegistrationPage() {
        return "register";
    }

    @GetMapping("/resend-code")
    public String getConfirmCodePage() {
        return "resend-code";
    }

    @PostMapping("")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return registrationService.register(registerRequest);
    }

    @PutMapping("/resend-code")
    public ResponseEntity<String> resendCode(@RequestParam String email) {
        return registrationService.resendCode(email);
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
                                                @RequestParam String token) {
        return registrationService.verifyAccount(email, token);
    }
}
