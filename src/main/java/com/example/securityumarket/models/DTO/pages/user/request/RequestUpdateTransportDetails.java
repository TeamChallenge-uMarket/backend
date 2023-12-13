package com.example.securityumarket.models.DTO.pages.user.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Builder
@Data
public class RequestUpdateTransportDetails {

    @Min(value = 1968, message = "year should not be earlier than 1968")
    @Max(value = 2023, message = "year should not be later than 2023")
    private Integer year;

    @Min(value = 0, message = "mileage should be a non-negative value")
    private Integer mileage;

    private Long bodyType;

    private Long city;

    @Pattern(regexp = "(^[A-Z0-9]{17}$)|^$", message = "Invalid vincode")
    private String vincode;

    @Size(max = 2000, message
            = "the text should be no more than 2000 characters")
    private String description;

    private Long transmission;

    private Long fuelType;

    @Min(value = 0, message = "fuel consumption in city should be a non-negative value")
    @Max(value = 50, message = "fuel consumption in city should be less then 50l per 100km")
    private Double consumptionCity;

    @Min(value = 0, message = "fuel consumption on highway should be a non-negative value")
    @Max(value = 50, message = "fuel consumption on highway should be less then 50l per 100km")
    private Double consumptionHighway;

    @Min(value = 0, message = "fuel consumption in mixed should be a non-negative value")
    @Max(value = 50, message = "fuel consumption in mixed should be less then 50l per 100km")
    private Double consumptionMixed;

    private Double engineDisplacement;

    private Integer enginePower;

    private Long driveType;

    private Integer numberOfDoors;

    private Integer numberOfSeats;

    private Long color;

    private Boolean accidentHistory;

    private Long condition;

    @Min(value = 0, message = "price should be a non-negative value")
    private BigDecimal price;

    private Boolean bargain;

    private Boolean trade;

    private Boolean military;

    private Boolean installmentPayment;

    private Boolean uncleared;

    private Integer loadCapacity;

    private Long producingCountry;

    private Long wheelConfiguration;

    private Long numberAxles;

    private String mainPhoto;
}
