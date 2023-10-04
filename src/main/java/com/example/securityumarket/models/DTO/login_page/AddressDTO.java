package com.example.securityumarket.models.DTO.login_page;

import lombok.Data;

@Data
public class AddressDTO {

    private String region;

    private String city;

    public boolean isEmpty() {
        return this.getCity().isBlank() && this.region.isBlank();
    }
}
