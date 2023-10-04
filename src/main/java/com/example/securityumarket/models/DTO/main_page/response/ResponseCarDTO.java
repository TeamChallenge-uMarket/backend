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
    private String imgUrl;
    private String carBrand;
    private String carModel;
    private BigDecimal price;
    private int mileage;
    private String city;
    private String transmission;
    private String fuelType;
    private int year;
    private String created;
}
