package com.example.securityumarket.util.converter;

import com.example.securityumarket.models.DTO.transports.impl.PassengerCarDTO;
import com.example.securityumarket.models.entities.Transport;
import org.springframework.stereotype.Component;

@Component
public class MotorizedFourWheeledVehicleConversionStrategy implements TransportTypeConversionStrategy {
    @Override
    public PassengerCarDTO createDTO(Transport transport) {
        return PassengerCarDTO.builder()
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
                .build();
    }
}
