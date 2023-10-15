package com.example.securityumarket.util.converter;

import com.example.securityumarket.models.DTO.transports.impl.MotorcycleDTO;
import com.example.securityumarket.models.entities.Transport;
import org.springframework.stereotype.Component;

@Component
public class MotorizedVehicleConversionStrategy implements TransportTypeConversionStrategy {
    @Override
    public MotorcycleDTO createDTO(Transport transport) {
        return MotorcycleDTO.builder()
                .transmission(transport.getTransmission())
                .fuelType(transport.getFuelType())
                .fuelConsumptionMixed(transport.getFuelConsumptionMixed())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .driveType(transport.getDriveType())
                .mileage(transport.getMileage())
                .build();
    }
}
