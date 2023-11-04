package com.example.securityumarket.models.DTO.entities.user;

import com.example.securityumarket.models.DTO.login_page.PasswordRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSecurityDetailsDTO extends PasswordRequest {
    @NotNull
    private String oldPassword;
}
