package com.example.securityumarket.models.DTO.transports;

/*
transmission - тип коробки передач (наприклад, автоматична, механічна).
fuelType - тип пального, яке використовується транспортним засобом (наприклад, бензин, дизель).
fuelConsumptionMixed - споживання пального в комбінованому циклі.
engineDisplacement - об'єм двигуна.
enginePower - потужність двигуна.
driveType - тип приводу (наприклад, передній, задній, повний).
mileage - пробіг в кілометрах
 */

public abstract class MotorizedVehicleDTO extends TransportDTO { // MOTO

    protected String transmission;

    protected String fuelType;

    protected double fuelConsumptionMixed;

    protected double engineDisplacement;

    protected int enginePower;

    protected String driveType;

    protected int mileage;
}
