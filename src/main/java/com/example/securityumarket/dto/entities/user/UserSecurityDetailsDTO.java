package com.example.securityumarket.dto.entities.user;

import com.example.securityumarket.dto.pages.login.PasswordRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserSecurityDetailsDTO extends PasswordRequest {
    private String oldPassword;
}
