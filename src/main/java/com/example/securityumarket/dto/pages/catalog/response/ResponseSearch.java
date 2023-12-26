package com.example.securityumarket.dto.pages.catalog.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ResponseSearch(
        Long id,
        String brand,
        String model,
        Integer year,
        BigDecimal price,
        Integer mileage,
        String description,
        String transmission,
        String fuelType,
        Double engineDisplacement,
        String city,
        LocalDateTime created,
        LocalDateTime lastUpdate,
        String fileUrl,
        Boolean isFavorite
) {}