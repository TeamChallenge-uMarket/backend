package com.example.securityumarket.util.converter;

import com.example.securityumarket.models.DTO.transports.LoadBearingVehicleDTO;
import com.example.securityumarket.models.entities.Transport;
import org.springframework.stereotype.Component;

@Component
public class LoadBearingVehicleConversionStrategy implements TransportTypeConversionStrategy {
    @Override
    public LoadBearingVehicleDTO createDTO(Transport transport) {
        return LoadBearingVehicleDTO.builder()
                .transmission(transport.getTransmission())
                .fuelType(transport.getFuelType())
                .fuelConsumptionMixed(transport.getFuelConsumptionMixed())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .driveType(transport.getDriveType())
                .mileage(transport.getMileage())
                .fuelConsumptionCity(transport.getFuelConsumptionCity())
                .fuelConsumptionHighway(transport.getFuelConsumptionHighway())
                .numberOfSeats(transport.getNumberOfSeats())
                .numberOfDoors(transport.getNumberOfDoors())
                .loadCapacity(transport.getLoadCapacity())
                .numberOfAxles(transport.getNumberOfAxles())
                .wheelConfiguration(transport.getWheelConfiguration())
                .build();
    }
}
