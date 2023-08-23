package com.example.securityumarket.controllers;

import com.example.securityumarket.models.PasswordRequest;
import com.example.securityumarket.services.MailService;
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

    private MailService mailService;
    private ResetPasswordService resetPasswordService;

    @PostMapping("")
    public ResponseEntity<String> sendCode(@RequestBody Map<String, String> requestEmail) {
        return mailService.sendCode(requestEmail.get("email"));
    }

    @PostMapping("/confirm-code")
    public ResponseEntity<String> confirmCode(@RequestBody Map<String, String> requestConfirmCode) {
        return mailService.confirmResetCode(requestConfirmCode.get("confirmCode"));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordRequest passwordRequest) {
        return resetPasswordService.resetPassword(passwordRequest);
    }
}
