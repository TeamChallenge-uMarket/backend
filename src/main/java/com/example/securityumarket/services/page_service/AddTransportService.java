package com.example.securityumarket.services.page_service;

import com.example.securityumarket.models.DTO.main_page.request.RequestAddTransportDTO;
import com.example.securityumarket.models.entities.*;
import com.example.securityumarket.services.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AddTransportService {

    private final UserService userService;

    private final TransportModelService transportModelService;

    private final CityService cityService;

    private final TransportGalleryService transportGalleryService;

    private final TransportService transportService;

    public ResponseEntity<String> addCar(RequestAddTransportDTO requestAddTransportDTO, MultipartFile[] multipartFiles) {
            Transport transport = buildCarFromRequestAddCarDTO(requestAddTransportDTO);
            transportService.save(transport);
            uploadFilesFromRequest(multipartFiles, transport);
            return ResponseEntity.ok("The ad with your transport has been successfully added.");
    }

    private void uploadFilesFromRequest(MultipartFile[] files, Transport transport) {
        transportGalleryService.uploadFiles(files, transport);
    }

    private City getCityFromRequestAddTransportDTO(String region, String city) {
        return cityService.findByRegionDescriptionAndDescription(region, city);
    }

    private Transport buildCarFromRequestAddCarDTO(RequestAddTransportDTO requestAddTransportDTO) {
        return Transport.builder()
                .user(getUser())
                .transportModel(getTransportModelFromRequestAddTransportDTO(requestAddTransportDTO.getModel()))
                .year(requestAddTransportDTO.getYear())
                .mileage(requestAddTransportDTO.getMileage())
//                .bodyType(requestAddTransportDTO.getBodyType())
                .city(getCityFromRequestAddTransportDTO(requestAddTransportDTO.getRegion(), requestAddTransportDTO.getCity()))
                .vincode(requestAddTransportDTO.getVincode())
                .description(requestAddTransportDTO.getDescription())
//                .transmission(requestAddTransportDTO.getTransmission())
//                .fuelType(requestAddTransportDTO.getFuelType())
                .fuelConsumptionCity(requestAddTransportDTO.getConsumptionCity())
                .fuelConsumptionHighway(requestAddTransportDTO.getConsumptionHighway())
                .fuelConsumptionMixed(requestAddTransportDTO.getConsumptionMixed())
                .engineDisplacement(requestAddTransportDTO.getEngineDisplacement())
                .enginePower(requestAddTransportDTO.getEnginePower())
//                .driveType(requestAddTransportDTO.getDriveType())
                .numberOfDoors(requestAddTransportDTO.getNumberOfDoors())
                .numberOfSeats(requestAddTransportDTO.getNumberOfSeats())
//                .color(requestAddTransportDTO.getColor())
//                .importedFrom(requestAddTransportDTO.getImportedFrom())
                .accidentHistory(requestAddTransportDTO.isAccidentHistory())
//                .condition(requestAddTransportDTO.getCondition())
                .price(requestAddTransportDTO.getPrice())
                .bargain(requestAddTransportDTO.isBargain())
                .trade(requestAddTransportDTO.isTrade())
                .military(requestAddTransportDTO.isMilitary())
                .installmentPayment(requestAddTransportDTO.isInstallmentPayment())
                .uncleared(requestAddTransportDTO.isUncleared())
                .build();
    }

    private Users getUser() {
        return userService.getAuthenticatedUser();
    }

    private TransportModel getTransportModelFromRequestAddTransportDTO(String model) {
        return transportModelService.findByModel(model);
    }
}
