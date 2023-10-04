package com.example.securityumarket.models.DTO;

import lombok.Data;

@Data
public class AddressDTO {
    private String region;
    private String city;

    public boolean isEmpty() {
        return this.getCity().isBlank() && this.region.isBlank();
    }
}
