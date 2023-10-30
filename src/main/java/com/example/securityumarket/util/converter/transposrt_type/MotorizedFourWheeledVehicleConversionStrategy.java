package com.example.securityumarket.util.converter.transposrt_type;

import com.example.securityumarket.models.DTO.transports.impl.PassengerCarDTO;
import com.example.securityumarket.models.entities.Transport;
import org.springframework.stereotype.Component;

@Component
public class MotorizedFourWheeledVehicleConversionStrategy implements TransportTypeConversionStrategy {
    @Override
    public PassengerCarDTO createDTO(Transport transport) {
        return PassengerCarDTO.builder()
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
                .build();
    }
}
