package com.example.securityumarket.models.DTO.main_page.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ResponseTransportDTO {

    private long transportId;

    private String imgUrl;

    private String transportBrand;

    private String transportModel;

    private BigDecimal price;

    private int mileage;

    private String city;

    private String transmission;

    private String fuelType;

    private int year;

    private String created;
}
