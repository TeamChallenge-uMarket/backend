package com.example.securityumarket.dto.pages.main.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Builder
public record RequestAddTransportDTO(
        @NotNull(message = "model is required")
        Long model,

        @NotNull(message = "bodyType is required")
        Long bodyType,

        Long importedFrom,
        @Min(value = 1968, message = "year should not be earlier than 1968")
        @Max(value = 2024, message = "year should not be later than 2023")
        @NotNull(message = "year is required")
        Integer year,

        @NotNull(message = "price is required")
        @Min(value = 0, message = "price should be a non-negative value")
        BigDecimal price,

        Boolean bargain,

        Boolean trade,

        Boolean military,

        Boolean installmentPayment,

        Boolean uncleared,

        Boolean accidentHistory,

        Long condition,

        @Pattern(regexp = "(^[A-Z0-9]{17}$)|^$", message = "Invalid vincode")
        String vincode,

        @Size(max = 2000, message
                = "the text should be no more than 2000 characters")
        String description,

        Long color,

        @NotNull(message = "city is required")
        Long city,

        @NotNull(message = "Main photo is required")
        String mainPhoto,

        Long transmission,

        Long fuelType,

        @Min(value = 0, message = "fuel consumption in city should be a non-negative value")
        @Max(value = 50, message = "fuel consumption in city should be less then 50l per 100km")
        Double fuelConsumptionCity,

        @Min(value = 0, message = "fuel consumption on highway should be a non-negative value")
        @Max(value = 50, message = "fuel consumption on highway should be less then 50l per 100km")
        Double fuelConsumptionHighway,

        @Min(value = 0, message = "fuel consumption in mixed should be a non-negative value")
        @Max(value = 50, message = "fuel consumption in mixed should be less then 50l per 100km")
        Double fuelConsumptionMixed,

        Double engineDisplacement,

        Integer enginePower,

        Long driveType,

        Integer mileage,

        Integer numberOfDoors,

        Integer numberOfSeats,

        Integer loadCapacity,

        Long wheelConfiguration,

        Long numberAxles
) {
}