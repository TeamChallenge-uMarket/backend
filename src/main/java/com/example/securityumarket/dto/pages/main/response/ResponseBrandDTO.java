package com.example.securityumarket.dto.pages.main.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseBrandDTO {

    private long brandId;

    private String brand;
}
