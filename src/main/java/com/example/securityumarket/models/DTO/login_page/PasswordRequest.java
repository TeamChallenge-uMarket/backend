package com.example.securityumarket.models.DTO.login_page;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordRequest {

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Confirmation is required")
    private String confirmPassword;

    @AssertTrue(message = "Password must be at least 8 characters long and contain at least one letter and one digit, and match the confirmation.")
    public boolean isPasswordValid() {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}") && password.equals(confirmPassword);
    }
}
