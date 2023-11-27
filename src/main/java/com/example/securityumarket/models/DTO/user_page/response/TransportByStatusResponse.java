package com.example.securityumarket.models.DTO.user_page.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransportByStatusResponse {

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

    private Integer viewCount;

    private Integer openedPhoneCount;

    private Integer addedFavoriteCount;

    private LocalDateTime lastUpdated;
}
