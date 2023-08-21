package com.example.securityumarket.models;

import com.example.securityumarket.models.DTO.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;
    private AddressDTO address;
}
