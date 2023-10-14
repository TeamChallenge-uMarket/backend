package com.example.securityumarket.util;


import com.example.securityumarket.models.DTO.transports.impl.*;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.services.jpa.TransportGalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TransportConverter {

    private final TransportGalleryService transportGalleryService;

    public MotorcycleDTO convertTransportToMotorcycleDTO(Transport transport) {
        return MotorcycleDTO.builder()
                .id(transport.getId())
                .bodyType(transport.getBodyType())
                .importedFrom(transport.getImportedFrom())
                .year(transport.getYear())
                .price(transport.getPrice())
                .bargain(transport.isBargain())
                .trade(transport.isTrade())
                .military(transport.isMilitary())
                .installmentPayment(transport.isInstallmentPayment())
                .uncleared(transport.isUncleared())
                .condition(transport.getCondition())
                .accidentHistory(transport.isAccidentHistory())
                .vincode(transport.getVincode())
                .description(transport.getDescription())
                .created(transport.getCreated())
                .lastUpdate(transport.getLastUpdate())
                .color(transport.getColor())
                .region(transport.getCity().getRegion().getDescription())
                .city(transport.getCity().getDescription())
                .mainPhoto(transportGalleryService.findMainFileByTransport(transport.getId()))
                .userName(transport.getUser().getName())
                .model(transport.getTransportModel().getModel())
                .brand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand())

                .transmission(transport.getTransmission())
                .fuelType(transport.getFuelType())
                .fuelConsumptionMixed(transport.getFuelConsumptionMixed())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .driveType(transport.getDriveType())
                .mileage(transport.getMileage())

                .build();
    }

    public PassengerCarDTO convertTransportToPassCarDTO(Transport transport) {
        return PassengerCarDTO.builder()
                .id(transport.getId())
                .bodyType(transport.getBodyType())
                .importedFrom(transport.getImportedFrom())
                .year(transport.getYear())
                .price(transport.getPrice())
                .bargain(transport.isBargain())
                .trade(transport.isTrade())
                .military(transport.isMilitary())
                .installmentPayment(transport.isInstallmentPayment())
                .uncleared(transport.isUncleared())
                .condition(transport.getCondition())
                .accidentHistory(transport.isAccidentHistory())
                .vincode(transport.getVincode())
                .description(transport.getDescription())
                .created(transport.getCreated())
                .lastUpdate(transport.getLastUpdate())
                .color(transport.getColor())
                .region(transport.getCity().getRegion().getDescription())
                .city(transport.getCity().getDescription())
                .mainPhoto(transportGalleryService.findMainFileByTransport(transport.getId()))
                .userName(transport.getUser().getName())
                .model(transport.getTransportModel().getModel())
                .brand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand())

                .transmission(transport.getTransmission())
                .fuelType(transport.getFuelType())
                .fuelConsumptionMixed(transport.getFuelConsumptionMixed())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .driveType(transport.getDriveType())
                .mileage(transport.getMileage())

                .fuelConsumptionCity(transport.getFuelConsumptionCity())
                .fuelConsumptionHighway(transport.getFuelConsumptionHighway())
                .numberOfDoors(transport.getNumberOfDoors())
                .numberOfSeats(transport.getNumberOfSeats())

                .build();
    }

    public AgriculturalDTO convertTransportToAgriculturalDTO(Transport transport) {
        return AgriculturalDTO.builder()
                .id(transport.getId())
                .bodyType(transport.getBodyType())
                .importedFrom(transport.getImportedFrom())
                .year(transport.getYear())
                .price(transport.getPrice())
                .bargain(transport.isBargain())
                .trade(transport.isTrade())
                .military(transport.isMilitary())
                .installmentPayment(transport.isInstallmentPayment())
                .uncleared(transport.isUncleared())
                .condition(transport.getCondition())
                .accidentHistory(transport.isAccidentHistory())
                .vincode(transport.getVincode())
                .description(transport.getDescription())
                .created(transport.getCreated())
                .lastUpdate(transport.getLastUpdate())
                .color(transport.getColor())
                .region(transport.getCity().getRegion().getDescription())
                .city(transport.getCity().getDescription())
                .mainPhoto(transportGalleryService.findMainFileByTransport(transport.getId()))
                .userName(transport.getUser().getName())
                .model(transport.getTransportModel().getModel())
                .brand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand())

                .transmission(transport.getTransmission())
                .fuelType(transport.getFuelType())
                .fuelConsumptionMixed(transport.getFuelConsumptionMixed())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .driveType(transport.getDriveType())
                .mileage(transport.getMileage())

                .fuelConsumptionCity(transport.getFuelConsumptionCity())
                .fuelConsumptionHighway(transport.getFuelConsumptionHighway())
                .numberOfDoors(transport.getNumberOfDoors())
                .numberOfSeats(transport.getNumberOfSeats())

                .loadCapacity(transport.getLoadCapacity())
                .numberOfAxles(transport.getNumberOfAxles())
                .wheelConfiguration(transport.getWheelConfiguration())

                .build();
    }

    public SpecializedVehicleDTO convertTransportToSpecializedVehicleDTO(Transport transport) {
        return SpecializedVehicleDTO.builder()
                .id(transport.getId())
                .bodyType(transport.getBodyType())
                .importedFrom(transport.getImportedFrom())
                .year(transport.getYear())
                .price(transport.getPrice())
                .bargain(transport.isBargain())
                .trade(transport.isTrade())
                .military(transport.isMilitary())
                .installmentPayment(transport.isInstallmentPayment())
                .uncleared(transport.isUncleared())
                .condition(transport.getCondition())
                .accidentHistory(transport.isAccidentHistory())
                .vincode(transport.getVincode())
                .description(transport.getDescription())
                .created(transport.getCreated())
                .lastUpdate(transport.getLastUpdate())
                .color(transport.getColor())
                .region(transport.getCity().getRegion().getDescription())
                .city(transport.getCity().getDescription())
                .mainPhoto(transportGalleryService.findMainFileByTransport(transport.getId()))
                .userName(transport.getUser().getName())
                .model(transport.getTransportModel().getModel())
                .brand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand())

                .transmission(transport.getTransmission())
                .fuelType(transport.getFuelType())
                .fuelConsumptionMixed(transport.getFuelConsumptionMixed())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .driveType(transport.getDriveType())
                .mileage(transport.getMileage())

                .fuelConsumptionCity(transport.getFuelConsumptionCity())
                .fuelConsumptionHighway(transport.getFuelConsumptionHighway())
                .numberOfDoors(transport.getNumberOfDoors())
                .numberOfSeats(transport.getNumberOfSeats())

                .loadCapacity(transport.getLoadCapacity())
                .numberOfAxles(transport.getNumberOfAxles())
                .wheelConfiguration(transport.getWheelConfiguration())

                .build();
    }

    public TruckDTO convertTransportToTruckDTO(Transport transport) {
        return TruckDTO.builder()
                .id(transport.getId())
                .bodyType(transport.getBodyType())
                .importedFrom(transport.getImportedFrom())
                .year(transport.getYear())
                .price(transport.getPrice())
                .bargain(transport.isBargain())
                .trade(transport.isTrade())
                .military(transport.isMilitary())
                .installmentPayment(transport.isInstallmentPayment())
                .uncleared(transport.isUncleared())
                .condition(transport.getCondition())
                .accidentHistory(transport.isAccidentHistory())
                .vincode(transport.getVincode())
                .description(transport.getDescription())
                .created(transport.getCreated())
                .lastUpdate(transport.getLastUpdate())
                .color(transport.getColor())
                .region(transport.getCity().getRegion().getDescription())
                .city(transport.getCity().getDescription())
                .mainPhoto(transportGalleryService.findMainFileByTransport(transport.getId()))
                .userName(transport.getUser().getName())
                .model(transport.getTransportModel().getModel())
                .brand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand())

                .transmission(transport.getTransmission())
                .fuelType(transport.getFuelType())
                .fuelConsumptionMixed(transport.getFuelConsumptionMixed())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .driveType(transport.getDriveType())
                .mileage(transport.getMileage())

                .fuelConsumptionCity(transport.getFuelConsumptionCity())
                .fuelConsumptionHighway(transport.getFuelConsumptionHighway())
                .numberOfDoors(transport.getNumberOfDoors())
                .numberOfSeats(transport.getNumberOfSeats())

                .loadCapacity(transport.getLoadCapacity())
                .numberOfAxles(transport.getNumberOfAxles())
                .wheelConfiguration(transport.getWheelConfiguration())

                .build();
    }

    public WaterVehicleDTO convertTransportToWaterVehicleDTO(Transport transport) {
        return WaterVehicleDTO.builder()
                .id(transport.getId())
                .bodyType(transport.getBodyType())
                .importedFrom(transport.getImportedFrom())
                .year(transport.getYear())
                .price(transport.getPrice())
                .bargain(transport.isBargain())
                .trade(transport.isTrade())
                .military(transport.isMilitary())
                .installmentPayment(transport.isInstallmentPayment())
                .uncleared(transport.isUncleared())
                .condition(transport.getCondition())
                .accidentHistory(transport.isAccidentHistory())
                .vincode(transport.getVincode())
                .description(transport.getDescription())
                .created(transport.getCreated())
                .lastUpdate(transport.getLastUpdate())
                .color(transport.getColor())
                .region(transport.getCity().getRegion().getDescription())
                .city(transport.getCity().getDescription())
                .mainPhoto(transportGalleryService.findMainFileByTransport(transport.getId()))
                .userName(transport.getUser().getName())
                .model(transport.getTransportModel().getModel())
                .brand(transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand())

                .fuelType(transport.getFuelType())
                .engineDisplacement(transport.getEngineDisplacement())
                .enginePower(transport.getEnginePower())
                .mileage(transport.getMileage())
                .numberOfSeats(transport.getNumberOfSeats())

                .build();
    }
}
