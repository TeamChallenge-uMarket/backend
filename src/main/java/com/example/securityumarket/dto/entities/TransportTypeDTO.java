package com.example.securityumarket.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransportTypeDTO {

    private long transportTypeId;

    private String transportType;
}
