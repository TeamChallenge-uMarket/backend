package com.example.securityumarket.services.page_service;

import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.DTO.main_page.request.RequestAddCarDTO;
import com.example.securityumarket.models.entities.*;
import com.example.securityumarket.services.jpa.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AddCarService {

    private final UserService userService;

    private final CarModelService carModelService;

    private final CityService cityService;

    private final FuelConsumptionService fuelConsumptionService;

    private final CarPriceService carPriceService;

    private final CarGalleryService carGalleryService;

    private final CarService carService;

    public ResponseEntity<String> addCar(RequestAddCarDTO requestAddCarDTO, MultipartFile[] multipartFiles) {
        try {
            Car car = buildCar(requestAddCarDTO, multipartFiles);
            carService.save(car);
            return ResponseEntity.ok("The ad with your car has been successfully added.");
        } catch (UAutoException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    private Car buildCar(RequestAddCarDTO requestAddCarDTO, MultipartFile[] multipartFiles) throws UAutoException {
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
                .fuelConsumption(getFuelConsumptionFromRequestAddCarDTO(
                        requestAddCarDTO.getConsumptionCity(),
                        requestAddCarDTO.getConsumptionHighway(),
                        requestAddCarDTO.getConsumptionMixed()
                ))
                .engineDisplacement(requestAddCarDTO.getEngineDisplacement())
                .enginePower(requestAddCarDTO.getEnginePower())
                .driveType(requestAddCarDTO.getDriveType())
                .numberOfDoors(requestAddCarDTO.getNumberOfDoors())
                .numberOfSeats(requestAddCarDTO.getNumberOfSeats())
                .color(requestAddCarDTO.getColor())
                .importedFrom(requestAddCarDTO.getImportedFrom())
                .accidentHistory(requestAddCarDTO.isAccidentHistory())
                .condition(requestAddCarDTO.getCondition())
                .price(getPriceFromRequestAddCarDTO(
                        requestAddCarDTO.getPrice(),
                        requestAddCarDTO.isBargain(),
                        requestAddCarDTO.isTrade(),
                        requestAddCarDTO.isMilitary(),
                        requestAddCarDTO.isInstallmentPayment(),
                        requestAddCarDTO.isUncleared()
                ))
                .carGalleries(getGalleryFromRequestAddCarDTO(multipartFiles))
                .build();
    }

    private List<CarGallery> getGalleryFromRequestAddCarDTO(MultipartFile[] files) throws UAutoException {
        return carGalleryService.findCarGalleriesByImageNames(files);
    }

    private CarPrice getPriceFromRequestAddCarDTO(
            BigDecimal price, boolean bargain, boolean trade,
            boolean military, boolean installmentPayment, boolean uncleared) throws UAutoException {
        return carPriceService.save(price, bargain, trade, military, installmentPayment, uncleared);
    }

    private FuelConsumption getFuelConsumptionFromRequestAddCarDTO(
            int consumptionCity, int consumptionHighway, int consumptionMixed) throws UAutoException {
        return fuelConsumptionService.save(consumptionCity, consumptionHighway, consumptionMixed);
    }

    private City getCityFromRequestAddCarDTO(String region, String city) throws UAutoException {
        return cityService.findByRegionDescriptionAndDescription(region, city);
    }

    private Users getUser() throws UAutoException {
        return userService.getAuthenticatedUser();
    }

    private CarModel getCarModelFromRequestAddCarDTO(String model) throws UAutoException {
        return carModelService.findByModel(model);
    }
}
