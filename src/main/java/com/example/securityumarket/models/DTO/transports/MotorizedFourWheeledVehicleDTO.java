package com.example.securityumarket.models.DTO.transports;

/*
fuelConsumptionCity - споживання пального в місті.
fuelConsumptionHighway - споживання пального на трасі.
numberOfDoors - кількість дверей.
numberOfSeats - кількість сидячих місць.
 */

public class MotorizedFourWheeledVehicleDTO extends MotorizedVehicleDTO { //PassengerCarDTO

    protected double fuelConsumptionCity;

    protected double fuelConsumptionHighway;

    protected int numberOfDoors;

    protected int numberOfSeats;
}
