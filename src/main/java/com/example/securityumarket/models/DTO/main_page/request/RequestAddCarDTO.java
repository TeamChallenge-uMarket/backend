package com.example.securityumarket.models.DTO.main_page.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestAddCarDTO {

    @NotBlank(message = "model is required")
    private String model;

    @Min(value = 1968, message = "year should not be earlier than 1968")
    @Max(value = 2023, message = "year should not be later than 2023")
    @NotNull(message = "year is required")
    private int year;

    private int mileage;

    private String bodyType;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "region is required")
    private String region;


    @Pattern(regexp = "(^[0-9]{17}$)|^$", message = "Invalid vincode")
    private String vincode;

    @Size(max = 2000, message
            = "the text should be no more than 2000 characters")
    private String description;

    private String transmission;

    private String fuelType;

    private int consumptionCity;

    private int consumptionHighway;

    private int consumptionMixed;

    private double engineDisplacement;

    private int enginePower;

    private String driveType;

    private int numberOfDoors;

    private int numberOfSeats;

    private String color;

    private String importedFrom;

    private boolean accidentHistory;

    private String condition;

    @NotNull(message = "price is required")
    @Min(value = 0, message = "price should be a non-negative value")
    private BigDecimal price;

    private boolean bargain;

    private boolean trade;

    private boolean military;

    private boolean installmentPayment;

    private boolean uncleared;
}
