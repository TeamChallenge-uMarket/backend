package com.example.securityumarket.dto.pages.catalog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSearchDTO {

    private Long id;

    private String brand;

    private String model;

    private Integer year;

    private BigDecimal price;

    private Integer mileage;
  
    private String description;

    private String transmission;

    private String fuelType;

    private Double engineDisplacement;

    private String city;

    private LocalDateTime created;

    private String fileUrl;

    private Boolean isFavorite;
}