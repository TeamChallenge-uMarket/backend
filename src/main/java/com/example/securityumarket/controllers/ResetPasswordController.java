package com.example.securityumarket.controllers;

import com.example.securityumarket.models.PasswordRequest;
import com.example.securityumarket.services.ResetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/v1/auth/reset-password")
public class ResetPasswordController {

    private ResetPasswordService resetPasswordService;

    @PostMapping("")
    public ResponseEntity<String> sendCode(@RequestBody Map<String, String> requestEmail) {
        return resetPasswordService.sendResetPasswordCode(requestEmail.get("email"));
    }

    @PostMapping("/confirm-code")
    public ResponseEntity<String> confirmCode(@RequestBody Map<String, String> requestConfirmCode) {
        return resetPasswordService.confirmResetPasswordCode(requestConfirmCode.get("confirmCode"));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordRequest passwordRequest) {
        return resetPasswordService.resetPassword(passwordRequest);
    }
}
