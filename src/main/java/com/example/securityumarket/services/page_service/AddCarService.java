package com.example.securityumarket.services.page_service;

import com.example.securityumarket.models.DTO.main_page.request.RequestAddCarDTO;
import com.example.securityumarket.models.entities.*;
import com.example.securityumarket.services.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AddCarService {

    private final UserService userService;

    private final CarModelService carModelService;

    private final CityService cityService;

    private final CarGalleryService carGalleryService;

    private final CarService carService;

    public ResponseEntity<String> addCar(RequestAddCarDTO requestAddCarDTO, MultipartFile[] multipartFiles) {
            Car car = buildCarFromRequestAddCarDTO(requestAddCarDTO);
            carService.save(car);
            uploadFilesFromRequest(multipartFiles, car);
            return ResponseEntity.ok("The ad with your car has been successfully added.");
    }

    private void uploadFilesFromRequest(MultipartFile[] files, Car car) {
        carGalleryService.uploadFiles(files, car);
    }

    private City getCityFromRequestAddCarDTO(String region, String city) {
        return cityService.findByRegionDescriptionAndDescription(region, city);
    }

    private Car buildCarFromRequestAddCarDTO(RequestAddCarDTO requestAddCarDTO) {
        return Car.builder()
                .user(getUser())
                .carModel(getCarModelFromRequestAddCarDTO(requestAddCarDTO.getModel()))
                .year(requestAddCarDTO.getYear())
                .mileage(requestAddCarDTO.getMileage())
                .bodyType(requestAddCarDTO.getBodyType())
                .city(getCityFromRequestAddCarDTO(requestAddCarDTO.getRegion(), requestAddCarDTO.getCity()))
                .vincode(requestAddCarDTO.getVincode())
                .description(requestAddCarDTO.getDescription())
                .transmission(requestAddCarDTO.getTransmission())
                .fuelType(requestAddCarDTO.getFuelType())
                .fuelConsumptionCity(requestAddCarDTO.getConsumptionCity())
                .fuelConsumptionHighway(requestAddCarDTO.getConsumptionHighway())
                .fuelConsumptionMixed(requestAddCarDTO.getConsumptionMixed())
                .engineDisplacement(requestAddCarDTO.getEngineDisplacement())
                .enginePower(requestAddCarDTO.getEnginePower())
                .driveType(requestAddCarDTO.getDriveType())
                .numberOfDoors(requestAddCarDTO.getNumberOfDoors())
                .numberOfSeats(requestAddCarDTO.getNumberOfSeats())
                .color(requestAddCarDTO.getColor())
                .importedFrom(requestAddCarDTO.getImportedFrom())
                .accidentHistory(requestAddCarDTO.isAccidentHistory())
                .condition(requestAddCarDTO.getCondition())
                .price(requestAddCarDTO.getPrice())
                .bargain(requestAddCarDTO.isBargain())
                .trade(requestAddCarDTO.isTrade())
                .military(requestAddCarDTO.isMilitary())
                .installmentPayment(requestAddCarDTO.isInstallmentPayment())
                .uncleared(requestAddCarDTO.isUncleared())
                .build();
    }

    private Users getUser() {
        return userService.getAuthenticatedUser();
    }

    private CarModel getCarModelFromRequestAddCarDTO(String model) {
        return carModelService.findByModel(model);
    }
}
