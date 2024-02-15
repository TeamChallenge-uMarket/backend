package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.entities.*;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.dto.pages.catalog.request.RequestFilterParam;
import com.example.securityumarket.dto.pages.catalog.request.RequestSearch;
import com.example.securityumarket.dto.pages.catalog.response.ResponseDefaultTransportParameter;
import com.example.securityumarket.dto.pages.catalog.response.ResponseSearch;
import com.example.securityumarket.dto.pages.catalog.response.impl.ResponseLoadBearingVehicleParameter;
import com.example.securityumarket.models.*;
import com.example.securityumarket.services.jpa.*;
import com.example.securityumarket.util.converter.transposrt_type.TransportConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.example.securityumarket.dao.specifications.TransportSpecifications.*;

@Slf4j
@RequiredArgsConstructor
@Service
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

    private final TransportConverter transportConverter;

    private final HiddenAdService hiddenAdService;

    private final HiddenUserService hiddenUserService;


    public void addFavorite(Long transportId) {
        Users user = userService.getAuthenticatedUser();
        Transport transport = transportService.findTransportById(transportId);
        favoriteTransportService.addFavorite(user, transport);

        log.info("Transport with ID {} added to favorites for user with ID {} successfully.",
                transport.getId(), user.getId());
    }

    public List<ResponseSearch> searchTransports(int page, int limit,
                                                 RequestSearch requestSearch) {
        List<Transport> transports = findTransportByParam(requestSearch,
                PageRequest.of(page, limit));
        return transportConverter.convertTransportListToResponseSearchDTO(transports);
    }

    public void removeFavorite(Long carId) {
        Users user = userService.getAuthenticatedUser();
        Transport transport = transportService.findTransportById(carId);
        favoriteTransportService.deleteFromFavoriteByUserAndTransport(user, transport);

        log.info("Transport with ID {} removed from favorites for user with ID {} successfully.",
                transport.getId(), user.getId());
    }

    public ResponseEntity<? extends ResponseDefaultTransportParameter> getFilterParameters(
            RequestFilterParam requestFilterParam) {
        TransportType transportType = transportTypeService
                .findById(requestFilterParam.getTransportTypeId());
        List<Long> transportBrandsId = requestFilterParam.getTransportBrandsId();

        if (isDefaultTransportType(transportType)) {
            return ResponseEntity.ok(buildResponseDefaultTransportParameter(transportType,
                    transportBrandsId));
        } else if (isLoadBearingVehicleType(transportType)) {
            return ResponseEntity.ok(buildResponseLoadBearingVehicleParameter(transportType,
                    transportBrandsId));
        } else {
            throw new DataNotValidException("Request filter param is not valid");
        }
    }

    private ResponseDefaultTransportParameter buildResponseDefaultTransportParameter(
            TransportType transportType, List<Long> transportBrands) {
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

    private ResponseLoadBearingVehicleParameter buildResponseLoadBearingVehicleParameter(
            TransportType transportType, List<Long> transportBrands) {
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

    private List<TransportModelDTO> getTransportModelDTOS(
            TransportType transportType, List<Long> transportBrands) {
        return transportModelService
                .findAllByTransportTypeAndBrandSpecification(transportType, transportBrands)
                .stream()
                .map(model ->
                        TransportModelDTO.builder()
                                .transportModelId(model.getId())
                                .model(model.getModel())
                                .build())
                .toList();
    }

    private List<BodyTypeDTO> getBodyTypeDTOS(TransportType transportType) {
        return bodyTypeService.findAllByTransportTypesId(transportType.getId())
                .stream()
                .map(bodyType ->
                        BodyTypeDTO.builder()
                                .bodyTypeId(bodyType.getId())
                                .bodyType(bodyType.getBodyType())
                                .build())
                .toList();
    }

    private List<DriveTypeDTO> getDriveTypeDTOS(TransportType transportType) {
        return driveTypeService.findAllByTransportTypesId(transportType.getId())
                .stream()
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
                                .hex(color.getHex())
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

    public List<Transport> findTransportByParam(RequestSearch requestSearch,
                                                PageRequest pageRequest) {
        return transportService.findAll(getSpecificationParam(requestSearch), pageRequest);
    }

    public Specification<Transport> getSpecificationParam(RequestSearch requestSearch) {
        return Specification.allOf(
                isActive()
                        .and(hasTransportTypeId(requestSearch.getTransportTypeId()))
                        .and(hasBrandId(requestSearch.getBrandId()))
                        .and(hasModelId(requestSearch.getModelId()))
                        .and(hasRegionId(requestSearch.getRegionId()))
                        .and(hasCityId(requestSearch.getCityId()))
                        .and(hasBodyTypeId(requestSearch.getBodyTypeId()))
                        .and(hasFuelTypeId(requestSearch.getFuelTypeId()))
                        .and(hasDriveTypeId(requestSearch.getDriveTypeId()))
                        .and(hasTransmissionId(requestSearch.getTransmissionId()))
                        .and(hasColorId(requestSearch.getColorId()))
                        .and(hasConditionId(requestSearch.getConditionId()))
                        .and(hasNumberAxlesId(requestSearch.getNumberAxlesId()))
                        .and(hasProducingCountryId(requestSearch.getProducingCountryId()))
                        .and(hasWheelConfigurationId(requestSearch.getWheelConfigurationId()))

                        .and(priceFrom(requestSearch.getPriceFrom()))
                        .and(priceTo(requestSearch.getPriceTo()))
                        .and(yearFrom(requestSearch.getYearsFrom()))
                        .and(yearTo(requestSearch.getYearsTo()))
                        .and(mileageFrom(requestSearch.getMileageFrom()))
                        .and(mileageTo(requestSearch.getMileageTo()))
                        .and(enginePowerFrom(requestSearch.getEnginePowerFrom()))
                        .and(enginePowerTo(requestSearch.getEnginePowerTo()))
                        .and(engineDisplacementFrom(requestSearch.getEngineDisplacementFrom()))
                        .and(engineDisplacementTo(requestSearch.getEngineDisplacementTo()))
                        .and(numberOfDoorsFrom(requestSearch.getNumberOfDoorsFrom()))
                        .and(numberOfDoorsTo(requestSearch.getNumberOfDoorsTo()))
                        .and(numberOfSeatsFrom(requestSearch.getNumberOfSeatsFrom()))
                        .and(numberOfSeatsTo(requestSearch.getNumberOfSeatsTo()))
                        .and(loadCapacityFrom(requestSearch.getLoadCapacityFrom()))
                        .and(loadCapacityTo(requestSearch.getLoadCapacityTo()))
                        .and(hasTrade(requestSearch.getTrade()))
                        .and(hasMilitary(requestSearch.getMilitary()))
                        .and(hasUncleared(requestSearch.getUncleared()))
                        .and(hasBargain(requestSearch.getBargain()))
                        .and(hasInstallmentPayment(requestSearch.getInstallmentPayment()))
                        .and(hiddenTransport(getHiddenTransportList()))
                        .and(sortBy(
                                requestSearch.getSortBy(), requestSearch.getOrderBy()))
        );
    }

    private List<Long> getHiddenTransportList() {
        if (userService.isUserAuthenticated()) {
            Users user = userService.getAuthenticatedUser();

            List<Long> hiddenAdTransportList = getTransportListFromHiddenAdByUser(user);
            List<Long> hiddenUserTransportList = getTransportListFromHiddenUserByUser(user);

            return Stream.concat(hiddenAdTransportList.stream(), hiddenUserTransportList.stream())
                    .distinct()
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    private List<Long> getTransportListFromHiddenAdByUser(Users user) {
        List<HiddenAd> allByUser = hiddenAdService.findAllByUser(user);
        return allByUser.stream()
                .map(hiddenAd -> hiddenAd
                        .getTransport()
                        .getId())
                .toList();
    }

    private List<Long> getTransportListFromHiddenUserByUser(Users user) {
        List<HiddenUser> allByUser = hiddenUserService.findAllByUser(user);
        return allByUser.stream()
                .flatMap(hiddenUser -> hiddenUser
                        .getHiddenUser()
                        .getTransports().stream()
                        .map(Transport::getId))
                .toList();
    }
}
