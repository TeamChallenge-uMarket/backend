package com.example.securityumarket.models.DTO.entities.user;

import com.example.securityumarket.models.DTO.login_page.PasswordRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserSecurityDetailsDTO extends PasswordRequest {
    private String oldPassword;
}
