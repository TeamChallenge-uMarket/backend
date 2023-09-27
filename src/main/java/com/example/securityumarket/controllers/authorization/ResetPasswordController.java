package com.example.securityumarket.controllers.authorization;

import com.example.securityumarket.models.PasswordRequest;
import com.example.securityumarket.services.authorization.ResetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/api/v1/authorization/reset-password")
public class ResetPasswordController {

    private ResetPasswordService resetPasswordService;

    @GetMapping("")
    public String getResetPasswordPage() {
        return "";
    }

    @GetMapping("/confirm-code")
    public String getConfirmCodePage() {
        return "confirm-code";
    }

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
