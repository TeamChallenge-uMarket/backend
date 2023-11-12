package com.example.securityumarket.services.pages;

import com.example.securityumarket.models.DTO.main_page.request.RequestAddTransportDTO;
import com.example.securityumarket.models.entities.*;
import com.example.securityumarket.services.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class AddTransportService {

    private final UserService userService;

    private final TransportModelService transportModelService;

    private final CityService cityService;

    private final TransportGalleryService transportGalleryService;

    private final TransportService transportService;

    private final BodyTypeService bodyTypeService;

    private final TransmissionService transmissionService;

    private final FuelTypeService fuelTypeService;

    private final DriveTypeService driveTypeService;

    private final TransportColorService transportColorService;

    private final ProducingCountryService producingCountryService;

    private final TransportConditionService transportConditionService;

    private final WheelConfigurationService wheelConfigurationService;

    private final NumberAxlesService numberAxlesService;

    @Transactional
    public ResponseEntity<String> addCar(RequestAddTransportDTO requestAddTransportDTO,
                                         MultipartFile[] multipartFiles) {
            Transport transport = buildCarFromRequestAddCarDTO(requestAddTransportDTO);
            transportService.save(transport);
            transportGalleryService.uploadFiles(
                    multipartFiles, requestAddTransportDTO.getMainPhoto(), transport);
            return ResponseEntity.ok("The ad with your transport has been successfully added.");
    }

    public Transport buildCarFromRequestAddCarDTO(RequestAddTransportDTO requestAddTransportDTO) {
        return Transport.builder()
                .user(userService.getAuthenticatedUser())
                .transportModel(getEntityFromRequest(
                        requestAddTransportDTO.getModel(), transportModelService::findById))
                .year(requestAddTransportDTO.getYear())
                .mileage(requestAddTransportDTO.getMileage())
                .bodyType(getEntityFromRequest(
                        requestAddTransportDTO.getBodyType(), bodyTypeService::findById))
                .city(getEntityFromRequest(
                        requestAddTransportDTO.getCity(), cityService::findById))
                .vincode(requestAddTransportDTO.getVincode())
                .description(requestAddTransportDTO.getDescription())
                .transmission(getEntityFromRequest(
                        requestAddTransportDTO.getTransmission(), transmissionService::findById))
                .fuelType(getEntityFromRequest(
                        requestAddTransportDTO.getFuelType(), fuelTypeService::findById))
                .fuelConsumptionCity(requestAddTransportDTO.getConsumptionCity())
                .fuelConsumptionHighway(requestAddTransportDTO.getConsumptionHighway())
                .fuelConsumptionMixed(requestAddTransportDTO.getConsumptionMixed())
                .engineDisplacement(requestAddTransportDTO.getEngineDisplacement())
                .enginePower(requestAddTransportDTO.getEnginePower())
                .driveType(getEntityFromRequest(
                        requestAddTransportDTO.getDriveType(), driveTypeService::findById))
                .numberOfDoors(requestAddTransportDTO.getNumberOfDoors())
                .numberOfSeats(requestAddTransportDTO.getNumberOfSeats())
                .transportColor(getEntityFromRequest(
                        requestAddTransportDTO.getColor(), transportColorService::findById))
                .producingCountry(getEntityFromRequest(
                        requestAddTransportDTO.getProducingCountry(), producingCountryService::findById))
                .accidentHistory(requestAddTransportDTO.getAccidentHistory())
                .transportCondition(getEntityFromRequest(
                        requestAddTransportDTO.getCondition(), transportConditionService::findById))
                .price(requestAddTransportDTO.getPrice())
                .bargain(requestAddTransportDTO.getBargain())
                .trade(requestAddTransportDTO.getTrade())
                .military(requestAddTransportDTO.getMilitary())
                .installmentPayment(requestAddTransportDTO.getInstallmentPayment())
                .uncleared(requestAddTransportDTO.getUncleared())
                .loadCapacity(requestAddTransportDTO.getLoadCapacity())
                .wheelConfiguration(getEntityFromRequest(
                        requestAddTransportDTO.getWheelConfiguration(), wheelConfigurationService::findById))
                .numberAxles(getEntityFromRequest(
                        requestAddTransportDTO.getNumberAxles(), numberAxlesService::findById))
                .status(Transport.Status.PENDING)
                .build();
    }


    private <T> T getEntityFromRequest(Long entityId, Function<Long, T> serviceMethod) {
        if (entityId != null) {
            return serviceMethod.apply(entityId);
        }
        return null;
    }
}
