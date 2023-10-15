package com.example.securityumarket.models.DTO.transports;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/*
fuelConsumptionCity - споживання пального в місті.
fuelConsumptionHighway - споживання пального на трасі.
numberOfDoors - кількість дверей.
numberOfSeats - кількість сидячих місць.
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class MotorizedFourWheeledVehicleDTO extends MotorizedVehicleDTO { //PassengerCarDTO

    protected double fuelConsumptionCity;

    protected double fuelConsumptionHighway;

    protected int numberOfDoors;

    protected int numberOfSeats;
}
