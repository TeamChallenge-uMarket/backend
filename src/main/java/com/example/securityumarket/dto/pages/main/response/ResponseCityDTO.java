package com.example.securityumarket.dto.pages.main.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCityDTO {

    private long cityId;

    private String city;

    private String region;
}
