package com.example.securityumarket.models.DTO.main_page.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCitiesDTO {
    private long cityId;
    private String city;
    private String region;
}
