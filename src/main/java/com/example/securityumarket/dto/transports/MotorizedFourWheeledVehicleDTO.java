package com.example.securityumarket.dto.transports;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class MotorizedFourWheeledVehicleDTO extends MotorizedVehicleDTO {

    protected double fuelConsumptionCity;

    protected double fuelConsumptionHighway;

    protected int numberOfDoors;

    protected int numberOfSeats;
}