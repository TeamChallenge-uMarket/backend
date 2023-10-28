package com.example.securityumarket.models.DTO.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransportConditionDTO {

    private long transportConditionId;

    private String transportCondition;
}
