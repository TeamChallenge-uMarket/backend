package com.example.securityumarket.dto.transports;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/*
fuelType - тип пального, яке використовується транспортним засобом (наприклад, бензин, дизель).
engineDisplacement - об'єм двигуна.
enginePower - потужність двигуна.
mileage - пробіг в кілометрах
numberOfDoors - кількість дверей.
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class WaterAbstractDTO extends TransportDTO { //WaterVehicleDTO

    protected String fuelType;

    protected double engineDisplacement;

    protected int enginePower;

    protected int mileage;

    protected int numberOfSeats;
}
