package com.example.securityumarket.models.DTO.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProducingCountryDTO {

    private long producingCountryId;

    private String producingCountry;
}
