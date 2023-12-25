package com.example.securityumarket.dto.pages.main.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModelDTO {

    private long modelId;

    private String brand;

    private String model;
}
