package com.example.securityumarket.models.DTO.main_page.response;

import com.example.securityumarket.models.entities.CarBrand;
import com.example.securityumarket.models.entities.CarModel;
import com.example.securityumarket.models.entities.City;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ResponseCarDTO {

    private long carId;
    private String imgUrlSmall;
    private CarBrand carBrand;
    private CarModel carModel;
    private BigDecimal price;
    private int mileage;
    private City city;
    private String transmission;
    private String fuelType;
    private int year;
}
