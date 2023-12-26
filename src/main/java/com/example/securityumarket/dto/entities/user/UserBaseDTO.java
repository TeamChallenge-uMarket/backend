package com.example.securityumarket.dto.entities.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserBaseDTO {
    private Long id;
    @Size(max = 255)
    private String name;
    @Email
    private String email;
}
