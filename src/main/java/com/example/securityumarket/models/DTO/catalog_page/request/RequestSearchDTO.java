package com.example.securityumarket.models.DTO.catalog_page.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class RequestSearchDTO {

    private String transportType;

    private String brand;

    private List<String> models;

    private Integer yearsFrom;

    private Integer yearsTo;

    private List<String> regions;

    private List<String> cities;

    private BigDecimal priceFrom;

    private BigDecimal priceTo;

    private List<String> bodyTypes;

    private List<String> fuelTypes;

    private List<String> transmissions;

    private List<String> colors;

    private List<String> conditions;

    private Integer mileageFrom;

    private Integer mileageTo;

    private Integer enginePowerFrom;

    private Integer enginePowerTo;

    private Integer engineDisplacementFrom;

    private Integer engineDisplacementTo;

    private List<String> driveTypes;

    private Integer numberOfDoorsFrom;

    private Integer numberOfDoorsTo;

    private Integer numberOfSeatsTo;

    private Integer numberOfSeatsFrom;

    private Boolean trade;

    private Boolean military;

    private Boolean uncleared;

    private Boolean bargain;

    private Boolean installmentPayment;
}