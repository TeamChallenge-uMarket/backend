package com.example.securityumarket.models.DTO.transports;

/*
fuelType - тип пального, яке використовується транспортним засобом (наприклад, бензин, дизель).
engineDisplacement - об'єм двигуна.
enginePower - потужність двигуна.
mileage - пробіг в кілометрах
numberOfDoors - кількість дверей.
 */

public abstract class WaterAbstractDTO extends TransportDTO {

    protected String fuelType;

    protected double engineDisplacement;

    protected int enginePower;

    protected int mileage;

    protected int numberOfSeats;
}
