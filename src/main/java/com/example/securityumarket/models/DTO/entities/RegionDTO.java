package com.example.securityumarket.models.DTO.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionDTO {

    private long regionId;

    private String region;
}
