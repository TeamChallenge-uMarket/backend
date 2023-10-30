package com.example.securityumarket.models.DTO.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransportModelDTO {

    private long transportModelId;

    private String model;
}
