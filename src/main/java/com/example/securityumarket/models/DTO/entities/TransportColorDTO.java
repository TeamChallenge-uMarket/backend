package com.example.securityumarket.models.DTO.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransportColorDTO {

    private long transportColorId;

    private String transportColor;
}
