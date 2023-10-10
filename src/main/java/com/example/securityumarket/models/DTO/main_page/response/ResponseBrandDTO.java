package com.example.securityumarket.models.DTO.main_page.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseBrandDTO {

    private long brandId;

    private String brand;
}
