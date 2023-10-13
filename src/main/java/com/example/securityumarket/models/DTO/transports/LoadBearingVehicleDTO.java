package com.example.securityumarket.models.DTO.transports;

/*
loadCapacity - вантажопідйомність в кілограмах.
numberOfAxles - кількість осей;
wheelConfiguration - колісна формула;
 */

public abstract class LoadBearingVehicleDTO { //Agricultural, SpecializedVehicle, Truck

    protected int loadCapacity;

    protected String numberOfAxles;

    protected String wheelConfiguration;
}
