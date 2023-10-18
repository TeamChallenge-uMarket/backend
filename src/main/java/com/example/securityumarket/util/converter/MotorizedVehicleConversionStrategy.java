package com.example.securityumarket.util.converter;

import com.example.securityumarket.models.DTO.transports.impl.MotorcycleDTO;
import com.example.securityumarket.models.entities.Transport;
import org.springframework.stereotype.Component;

@Component
public class MotorizedVehicleConversionStrategy implements TransportTypeConversionStrategy {
    @Override
    public MotorcycleDTO createDTO(Transport transport) {
        return MotorcycleDTO.builder()
                .transmission(transport.getTransmission().getTransmission())
                .fuelType(transport.getFuelType().getFuelType())
                .fuelConsumptionMixed(transport.getFuelConsumptionMixed())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .driveType(transport.getDriveType().getDriveType())
                .mileage(transport.getMileage())
                .build();
    }
}
