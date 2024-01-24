package com.example.securityumarket.dto.transports;


import com.example.securityumarket.dto.entities.TransportGalleryDTO;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Builder
@Data
public class TransportDTO {

    protected long id;

    protected String bodyType;

    protected String importedFrom;

    protected int year;

    protected BigDecimal price;

    protected boolean bargain;

    protected boolean trade;

    protected boolean military;

    protected boolean installmentPayment;

    protected boolean uncleared;

    protected String condition;

    protected boolean accidentHistory;

    protected String vincode;

    protected String description;

    protected LocalDateTime created;

    protected LocalDateTime lastUpdate;

    protected String color;

    protected String region;

    protected String city;

    protected String mainPhoto;

    protected String userName;

    protected String model;

    protected String brand;

    protected String phone;

    protected List<TransportGalleryDTO> galleries;
}
