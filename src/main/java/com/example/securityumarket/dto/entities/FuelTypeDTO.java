package com.example.securityumarket.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuelTypeDTO {

    private long fuelTypeId;

    private String fuelType;
}
