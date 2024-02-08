package com.example.securityumarket.dto.pages.login;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
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

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Confirmation is required")
    private String confirmPassword;

    @AssertTrue(message = "Password must be at least 8 characters long, less than 50 characters "
            + "and contain at least one letter and one digit, and match the confirmation.")
    public boolean isPasswordValid() {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d#?!@$%^&~*(){}:;_+|<>/.,'=№`\\-\"\\[\\]]{8,49}")
                && password.equals(confirmPassword);
    }

}
