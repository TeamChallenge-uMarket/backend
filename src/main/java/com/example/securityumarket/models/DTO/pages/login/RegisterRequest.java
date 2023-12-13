package com.example.securityumarket.models.DTO.pages.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends PasswordRequest {

    @NotBlank(message = "username is required")
    @Size(min = 2, max = 50, message
            = "user name must be between 2 and 50 characters")
    private String name;
}
