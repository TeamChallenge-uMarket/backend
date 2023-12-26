package com.example.securityumarket.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransportBrandDTO {

    private long transportBrandId;

    private String brand;
}
