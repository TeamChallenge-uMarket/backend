package com.example.securityumarket.models.DTO.catalog;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MotorcycleFilterDTO {
    private String transportType;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private String bodyType;
    //TODO need to know what is it =|
   // private String additionalOptions;
    private String fuelType;
    private String transmission;
    private String color;
    private String condition;
    private int mileageFrom;
    private int mileageTo;
    private int enginePowerFrom;
    private int enginePowerTo;
    private String driveType;
    //order by and sort by using in request param like String
    private String orderBy = "created";
    private String sortBy = "desc";
}
