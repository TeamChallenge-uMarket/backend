package com.example.securityumarket.models.DTO.entities.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
public class UserBaseDTO {
    private Long id;
    @Size(max = 255)
    private String name;
    @Email
    private String email;
}
