package com.example.securityumarket.util.converter;

import com.example.securityumarket.models.DTO.transports.impl.WaterVehicleDTO;
import com.example.securityumarket.models.entities.Transport;
import org.springframework.stereotype.Component;

@Component
public class WaterVehicleConversionStrategy implements TransportTypeConversionStrategy {
    @Override
    public WaterVehicleDTO createDTO(Transport transport) {
        return WaterVehicleDTO.builder()
                .fuelType(transport.getFuelType().getFuelType())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .mileage(transport.getMileage())
                .numberOfSeats(transport.getNumberOfSeats())
                .build();
    }
}
