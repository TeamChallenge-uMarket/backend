package com.example.securityumarket.services.pages;

import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.models.DTO.pages.catalog.request.RequestFilterParam;
import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.DTO.pages.catalog.response.ResponseDefaultTransportParameter;
import com.example.securityumarket.models.DTO.pages.catalog.response.ResponseSearchDTO;
import com.example.securityumarket.models.DTO.pages.catalog.response.impl.ResponseLoadBearingVehicleParameter;
import com.example.securityumarket.models.DTO.entities.*;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportType;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.securityumarket.models.specifications.TransportSpecifications.*;


@Service
@RequiredArgsConstructor
public class CatalogPageService {

    private final FavoriteTransportService favoriteTransportService;

    private final UserService userService;

    private final TransportService transportService;

    private final TransportTypeService transportTypeService;

    private final BodyTypeService bodyTypeService;

    private final DriveTypeService driveTypeService;

    private final FuelTypeService fuelTypeService;

    private final ProducingCountryService producingCountryService;

    private final TransmissionService transmissionService;

    private final TransportColorService transportColorService;

    private final TransportConditionService transportConditionService;

    private final WheelConfigurationService wheelConfigurationService;

    private final NumberAxlesService numberAxlesService;

    private final TransportBrandService transportBrandService;

    private final TransportModelService transportModelService;


    public void addFavorite(long carId) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        Transport transport = transportService.findTransportById(carId);
        favoriteTransportService.addFavorite(authenticatedUser, transport);
    }

    public List<ResponseSearchDTO> searchTransports(int page, int limit, RequestSearchDTO requestSearchDTO) {
        List<Transport> transports = findTransportByParam(requestSearchDTO, PageRequest.of(page, limit));
        return transportService.convertTransportListToTransportSearchDTO(transports);
    }

    public void removeFavorite(long carId) {
        Users user = userService.getAuthenticatedUser();
        Transport transport = transportService.findTransportById(carId);
        favoriteTransportService.deleteFromFavoriteByUserAndTransport(user, transport);
    }

    public ResponseEntity<? extends ResponseDefaultTransportParameter> getFilterParameters(RequestFilterParam requestFilterParam) {
        TransportType transportType = transportTypeService.findById(requestFilterParam.getTransportTypeId());
        List<Long> transportBrandsId = requestFilterParam.getTransportBrandsId();
        if (isDefaultTransportType(transportType)) {
            return ResponseEntity.ok(buildResponseDefaultTransportParameter(transportType, transportBrandsId));
        } else if (isLoadBearingVehicleType(transportType)) {
            return ResponseEntity.ok(buildResponseLoadBearingVehicleParameter(transportType, transportBrandsId));
        } else {
            throw new DataNotValidException("Request filter param is not valid");
        }
    }

    private ResponseDefaultTransportParameter buildResponseDefaultTransportParameter(TransportType transportType, List<Long> transportBrands) {
        return ResponseDefaultTransportParameter.builder()
                .transportBrandDTOS(getTransportBrandDTOS(transportType))
                .transportModelDTOS(getTransportModelDTOS(transportType, transportBrands))
                .bodyTypeDTOS(getBodyTypeDTOS(transportType))
                .driveTypeDTOS(getDriveTypeDTOS(transportType))
                .fuelTypeDTOS(getFuelTypeDTOS())
                .producingCountryDTOS(getProducingCountryDTOS())
                .transmissionDTOS(getTransmissionDTOS())
                .transportColorDTOS(getTransportColorDTOS())
                .transportConditionDTOS(getTransportConditionDTOS())
                .build();
    }

    private ResponseLoadBearingVehicleParameter buildResponseLoadBearingVehicleParameter(TransportType transportType, List<Long> transportBrands) {
        return ResponseLoadBearingVehicleParameter.builder()
                .transportBrandDTOS(getTransportBrandDTOS(transportType))
                .transportModelDTOS(getTransportModelDTOS(transportType, transportBrands))
                .bodyTypeDTOS(getBodyTypeDTOS(transportType))
                .driveTypeDTOS(getDriveTypeDTOS(transportType))
                .fuelTypeDTOS(getFuelTypeDTOS())
                .producingCountryDTOS(getProducingCountryDTOS())
                .transmissionDTOS(getTransmissionDTOS())
                .transportColorDTOS(getTransportColorDTOS())
                .transportConditionDTOS(getTransportConditionDTOS())
                .numberAxlesDTOS(getNumberAxlesDTOS())
                .wheelConfigurationDTOS(getWheelConfigurationDTOS())
                .build();
    }


    private boolean isDefaultTransportType(TransportType transportType) {
        int id = Math.toIntExact(transportType.getId());
        return id == 1 || id == 2 || id == 6;
    }

    private boolean isLoadBearingVehicleType(TransportType transportType) {
        int id = Math.toIntExact(transportType.getId());
        return id == 3 || id == 4 || id == 5;
    }

    private List<TransportBrandDTO> getTransportBrandDTOS(TransportType transportType) {
        return transportBrandService.findAllSpecification(transportType.getId()).stream()
                .map(transportBrand ->
                        TransportBrandDTO.builder()
                                .transportBrandId(transportBrand.getId())
                                .brand(transportBrand.getBrand())
                                .build())
                .toList();
    }

    private List<TransportModelDTO> getTransportModelDTOS(TransportType transportType, List<Long> transportBrands) {
        return transportModelService.findAllByTransportTypeAndBrandSpecification(transportType, transportBrands).stream()
                .map(model ->
                        TransportModelDTO.builder()
                                .transportModelId(model.getId())
                                .model(model.getModel())
                                .build())
                .toList();
    }

    private List<BodyTypeDTO> getBodyTypeDTOS(TransportType transportType) {
        return bodyTypeService.findAllByTransportTypesId(transportType.getId()).stream()
                .map(bodyType ->
                        BodyTypeDTO.builder()
                                .bodyTypeId(bodyType.getId())
                                .bodyType(bodyType.getBodyType())
                                .build())
                .toList();
    }

    private List<DriveTypeDTO> getDriveTypeDTOS(TransportType transportType) {
        return driveTypeService.findAllByTransportTypesId(transportType.getId()).stream()
                .map(driveType ->
                        DriveTypeDTO.builder()
                                .driveTypeId(driveType.getId())
                                .driveType(driveType.getDriveType())
                                .build())
                .toList();
    }

    private List<FuelTypeDTO> getFuelTypeDTOS() {
        return fuelTypeService.findAll().stream()
                .map(fuelType ->
                        FuelTypeDTO.builder()
                                .fuelTypeId(fuelType.getId())
                                .fuelType(fuelType.getFuelType())
                                .build())
                .toList();
    }

    private List<ProducingCountryDTO> getProducingCountryDTOS() {
        return producingCountryService.findAll().stream()
                .map(producingCountry ->
                        ProducingCountryDTO.builder()
                                .producingCountryId(producingCountry.getId())
                                .producingCountry(producingCountry.getCountry())
                                .build())
                .toList();
    }

    private List<TransmissionDTO> getTransmissionDTOS() {
        return transmissionService.findAll().stream()
                .map(transmission ->
                        TransmissionDTO.builder()
                                .transmissionId(transmission.getId())
                                .transmission(transmission.getTransmission())
                                .build())
                .toList();
    }

    private List<TransportColorDTO> getTransportColorDTOS() {
        return transportColorService.findAll().stream()
                .map(color ->
                        TransportColorDTO.builder()
                                .transportColorId(color.getId())
                                .transportColor(color.getColor())
                                .build())
                .toList();
    }

    private List<TransportConditionDTO> getTransportConditionDTOS() {
        return transportConditionService.findAll().stream()
                .map(condition ->
                        TransportConditionDTO.builder()
                                .transportConditionId(condition.getId())
                                .transportCondition(condition.getCondition())
                                .build())
                .toList();
    }

    private List<WheelConfigurationDTO> getWheelConfigurationDTOS() {
        return wheelConfigurationService.findAll().stream()
                .map(wheelConfiguration ->
                        WheelConfigurationDTO.builder()
                                .wheelConfigurationId(wheelConfiguration.getId())
                                .wheelConfiguration(wheelConfiguration.getWheelConfiguration())
                                .build())
                .toList();
    }

    private List<NumberAxlesDTO> getNumberAxlesDTOS() {
        return numberAxlesService.findAll().stream()
                .map(numberAxles ->
                        NumberAxlesDTO.builder()
                                .numberAxlesId(numberAxles.getId())
                                .numberAxles(numberAxles.getNumberAxles())
                                .build())
                .toList();
    }

    public List<Transport> findTransportByParam(RequestSearchDTO requestSearchDTO, PageRequest pageRequest) {
        return transportService.findAll(getSpecificationParam(requestSearchDTO), pageRequest);
    }

    public Specification<Transport> getSpecificationParam(RequestSearchDTO requestSearchDTO) {
        return Specification.allOf(
                isActive()
                        .and(hasTransportTypeId(requestSearchDTO.getTransportTypeId()))
                        .and(hasBrandId(requestSearchDTO.getBrandId()))
                        .and(hasModelId(requestSearchDTO.getModelId()))
                        .and(hasRegionId(requestSearchDTO.getRegionId()))
                        .and(hasCityId(requestSearchDTO.getCityId()))
                        .and(hasBodyTypeId(requestSearchDTO.getBodyTypeId()))
                        .and(hasFuelTypeId(requestSearchDTO.getFuelTypeId()))
                        .and(hasDriveTypeId(requestSearchDTO.getDriveTypeId()))
                        .and(hasTransmissionId(requestSearchDTO.getTransmissionId()))
                        .and(hasColorId(requestSearchDTO.getColorId()))
                        .and(hasConditionId(requestSearchDTO.getConditionId()))
                        .and(hasNumberAxlesId(requestSearchDTO.getNumberAxlesId()))
                        .and(hasProducingCountryId(requestSearchDTO.getProducingCountryId()))
                        .and(hasWheelConfigurationId(requestSearchDTO.getWheelConfigurationId()))

                        .and(priceFrom(requestSearchDTO.getPriceFrom()))
                        .and(priceTo(requestSearchDTO.getPriceTo()))
                        .and(yearFrom(requestSearchDTO.getYearsFrom()))
                        .and(yearTo(requestSearchDTO.getYearsTo()))
                        .and(mileageFrom(requestSearchDTO.getMileageFrom()))
                        .and(mileageTo(requestSearchDTO.getMileageTo()))
                        .and(enginePowerFrom(requestSearchDTO.getEnginePowerFrom()))
                        .and(enginePowerTo(requestSearchDTO.getEnginePowerTo()))
                        .and(engineDisplacementFrom(requestSearchDTO.getEngineDisplacementFrom()))
                        .and(engineDisplacementTo(requestSearchDTO.getEngineDisplacementTo()))
                        .and(numberOfDoorsFrom(requestSearchDTO.getNumberOfDoorsFrom()))
                        .and(numberOfDoorsTo(requestSearchDTO.getNumberOfDoorsTo()))
                        .and(numberOfSeatsFrom(requestSearchDTO.getNumberOfSeatsFrom()))
                        .and(numberOfSeatsTo(requestSearchDTO.getNumberOfSeatsTo()))
                        .and(loadCapacityFrom(requestSearchDTO.getLoadCapacityFrom()))
                        .and(loadCapacityTo(requestSearchDTO.getLoadCapacityTo()))
                        .and(hasTrade(requestSearchDTO.getTrade()))
                        .and(hasMilitary(requestSearchDTO.getMilitary()))
                        .and(hasUncleared(requestSearchDTO.getUncleared()))
                        .and(hasBargain(requestSearchDTO.getBargain()))
                        .and(hasInstallmentPayment(requestSearchDTO.getInstallmentPayment()))
                        .and(sortBy(Transport.class, requestSearchDTO.getSortBy(), requestSearchDTO.getOrderBy()))
        );
    }
}
