package com.example.securityumarket.dto.transports;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class MotorizedVehicleDTO extends TransportDTO {

    protected String transmission;

    protected String fuelType;

    protected double fuelConsumptionMixed;

    protected double engineDisplacement;

    protected int enginePower;

    protected String driveType;

    protected int mileage;
}
