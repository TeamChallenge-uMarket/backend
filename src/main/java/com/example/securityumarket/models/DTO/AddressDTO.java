package com.example.securityumarket.models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDTO {
    @NotBlank(message = "[Address] The country is required.")
    private String country;

    @NotBlank(message = "[Address] The city name is required.")
    private String city;
}
