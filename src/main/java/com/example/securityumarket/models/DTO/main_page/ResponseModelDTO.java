package com.example.securityumarket.models.DTO.main_page;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModelDTO {
    private long modelId;
    private String brand;
    private String model;
}
