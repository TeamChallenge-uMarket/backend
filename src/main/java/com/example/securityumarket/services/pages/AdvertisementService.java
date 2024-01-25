package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.pages.main.request.RequestAddTransportDTO;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.services.jpa.*;
import com.example.securityumarket.services.storage.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdvertisementService {

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

    private final SubscriptionPageService subscriptionPageService;

    private final CloudinaryService cloudinaryService;

    //TODO ADD NECESSARY FIELDS
    @Transactional
    public void addAdvertisement(RequestAddTransportDTO requestAddTransportDTO,
                                 MultipartFile[] multipartFiles) {
        Transport transport = buildCarFromRequestAddCarDTO(requestAddTransportDTO);
        transportService.save(transport);

        subscriptionPageService.notifyUsers(transport);

        transportGalleryService.uploadFiles(
                multipartFiles, requestAddTransportDTO.mainPhoto(), transport);

        log.info("Transport with ID {} added successfully.", transport.getId());

    }

    public Transport buildCarFromRequestAddCarDTO(RequestAddTransportDTO requestAddTransportDTO) {
        return Transport.builder()
                .user(userService.getAuthenticatedUser())
                .transportModel(getEntityFromRequest(
                        requestAddTransportDTO.model(), transportModelService::findById))
                .bodyType(getEntityFromRequest(
                        requestAddTransportDTO.bodyType(), bodyTypeService::findById))
                .producingCountry(getEntityFromRequest(
                        requestAddTransportDTO.importedFrom(), producingCountryService::findById))
                .year(requestAddTransportDTO.year())
                .price(requestAddTransportDTO.price())
                .bargain(requestAddTransportDTO.bargain())
                .trade(requestAddTransportDTO.trade())
                .military(requestAddTransportDTO.military())
                .installmentPayment(requestAddTransportDTO.installmentPayment())
                .uncleared(requestAddTransportDTO.uncleared())
                .accidentHistory(requestAddTransportDTO.accidentHistory())
                .transportCondition(getEntityFromRequest(
                        requestAddTransportDTO.condition(), transportConditionService::findById))
                .vincode(requestAddTransportDTO.vincode())
                .description(requestAddTransportDTO.description())
                .transportColor(getEntityFromRequest(
                        requestAddTransportDTO.color(), transportColorService::findById))
                .city(getEntityFromRequest(
                        requestAddTransportDTO.city(), cityService::findById))
                .transmission(getEntityFromRequest(
                        requestAddTransportDTO.transmission(), transmissionService::findById))
                .fuelType(getEntityFromRequest(
                        requestAddTransportDTO.fuelType(), fuelTypeService::findById))
                .fuelConsumptionCity(requestAddTransportDTO.fuelConsumptionCity())
                .fuelConsumptionHighway(requestAddTransportDTO.fuelConsumptionHighway())
                .fuelConsumptionMixed(requestAddTransportDTO.fuelConsumptionMixed())
                .engineDisplacement(requestAddTransportDTO.engineDisplacement())
                .enginePower(requestAddTransportDTO.enginePower())
                .driveType(getEntityFromRequest(
                        requestAddTransportDTO.driveType(), driveTypeService::findById))
                .mileage(requestAddTransportDTO.mileage())
                .numberOfDoors(requestAddTransportDTO.numberOfDoors())
                .numberOfSeats(requestAddTransportDTO.numberOfSeats())
                .loadCapacity(requestAddTransportDTO.loadCapacity())
                .wheelConfiguration(getEntityFromRequest(
                        requestAddTransportDTO.wheelConfiguration(), wheelConfigurationService::findById))
                .numberAxles(getEntityFromRequest(
                        requestAddTransportDTO.numberAxles(), numberAxlesService::findById))
                .status(Transport.Status.ACTIVE)
                .build();
    }


    private <T> T getEntityFromRequest(Long entityId, Function<Long, T> serviceMethod) {
        if (entityId != null) {
            return serviceMethod.apply(entityId);
        }
        return null;
    }
}
