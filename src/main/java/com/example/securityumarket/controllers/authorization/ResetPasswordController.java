package com.example.securityumarket.controllers.authorization;

import com.example.securityumarket.models.DTO.login_page.PasswordRequest;
import com.example.securityumarket.services.authorization.ResetPasswordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/v1/authorization/reset-password")
@Tag(name = "Reset passwords", description = "Reset password endpoints: reset-password, form, send-code, resend-code, verify account")
public class ResetPasswordController {

    private ResetPasswordService resetPasswordService;

    @GetMapping("")
    public String getResetPasswordPage() {
        return "reset-password";
    }

    @GetMapping("/form")
    public String getConfirmCodePage() {
        return "reset-password form";
    }

    @PutMapping("/send-code")
    public ResponseEntity<String> sendCode(@RequestParam String email) {
        return resetPasswordService.sendResetPasswordCode(email);
    }

    @PutMapping("")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
                                                @RequestParam String token) {
        return resetPasswordService.verifyAccount(email, token);
    }

    @PostMapping("")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid PasswordRequest passwordRequest) {
        return resetPasswordService.resetPassword(passwordRequest);
    }
}
