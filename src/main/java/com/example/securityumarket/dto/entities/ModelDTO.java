package com.example.securityumarket.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelDTO {

    private long modelId;

    private String model;
}
