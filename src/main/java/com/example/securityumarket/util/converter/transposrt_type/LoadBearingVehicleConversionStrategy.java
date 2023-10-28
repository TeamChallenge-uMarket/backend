package com.example.securityumarket.util.converter.transposrt_type;

import com.example.securityumarket.models.DTO.transports.LoadBearingVehicleDTO;
import com.example.securityumarket.models.entities.Transport;
import org.springframework.stereotype.Component;

@Component
public class LoadBearingVehicleConversionStrategy implements TransportTypeConversionStrategy {
    @Override
    public LoadBearingVehicleDTO createDTO(Transport transport) {
        return LoadBearingVehicleDTO.builder()
                .transmission(transport.getTransmission().getTransmission())
                .fuelType(transport.getFuelType().getFuelType())
                .fuelConsumptionMixed(transport.getFuelConsumptionMixed())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .driveType(transport.getDriveType().getDriveType())
                .mileage(transport.getMileage())
                .fuelConsumptionCity(transport.getFuelConsumptionCity())
                .fuelConsumptionHighway(transport.getFuelConsumptionHighway())
                .numberOfSeats(transport.getNumberOfSeats())
                .numberOfDoors(transport.getNumberOfDoors())
                .loadCapacity(transport.getLoadCapacity())
                .wheelConfiguration(transport.getWheelConfiguration().getWheelConfiguration())
                .build();
    }
}
