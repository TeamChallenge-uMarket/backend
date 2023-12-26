package com.example.securityumarket.dto.transports;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class WaterAbstractDTO extends TransportDTO {

    protected String fuelType;

    protected double engineDisplacement;

    protected int enginePower;

    protected int mileage;

    protected int numberOfSeats;
}
