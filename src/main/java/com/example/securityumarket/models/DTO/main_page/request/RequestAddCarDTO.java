package com.example.securityumarket.models.DTO.main_page.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestAddCarDTO {
    private String model;
    private int year;
    private int mileage;
    private String bodyType;
    private String city;
    private String region;
    private String vincode;
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
    private BigDecimal price;
    private boolean bargain;
    private boolean trade;
    private boolean military;
    private boolean installmentPayment;
    private boolean uncleared;
//    private MultipartFile[] files;
}
