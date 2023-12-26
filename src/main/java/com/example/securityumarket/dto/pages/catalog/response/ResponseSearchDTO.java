package com.example.securityumarket.dto.pages.catalog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record ResponseSearchDTO (
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