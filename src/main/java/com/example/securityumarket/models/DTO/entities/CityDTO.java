package com.example.securityumarket.models.DTO.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDTO {

    private long cityId;

    private String city;
}
