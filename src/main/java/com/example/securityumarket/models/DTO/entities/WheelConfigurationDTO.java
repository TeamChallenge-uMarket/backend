package com.example.securityumarket.models.DTO.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WheelConfigurationDTO {

    private long wheelConfigurationId;

    private String wheelConfiguration;
}
