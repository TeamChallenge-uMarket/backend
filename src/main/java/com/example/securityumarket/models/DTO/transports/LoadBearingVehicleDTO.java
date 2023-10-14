package com.example.securityumarket.models.DTO.transports;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/*
loadCapacity - вантажопідйомність в кілограмах.
numberOfAxles - кількість осей;
wheelConfiguration - колісна формула;
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public abstract class LoadBearingVehicleDTO extends MotorizedFourWheeledVehicleDTO { //Agricultural, SpecializedVehicle, Truck

    protected int loadCapacity;

    protected String numberOfAxles;

    protected String wheelConfiguration;
}
