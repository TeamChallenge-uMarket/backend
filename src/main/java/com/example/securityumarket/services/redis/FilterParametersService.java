package com.example.securityumarket.services.redis;

import com.example.securityumarket.dto.entities.BodyTypeDTO;
import com.example.securityumarket.dto.entities.DriveTypeDTO;
import com.example.securityumarket.dto.entities.FuelTypeDTO;
import com.example.securityumarket.dto.entities.ModelDTO;
import com.example.securityumarket.dto.entities.NumberAxlesDTO;
import com.example.securityumarket.dto.entities.ProducingCountryDTO;
import com.example.securityumarket.dto.entities.TransmissionDTO;
import com.example.securityumarket.dto.entities.TransportBrandDTO;
import com.example.securityumarket.dto.entities.TransportColorDTO;
import com.example.securityumarket.dto.entities.TransportConditionDTO;
import com.example.securityumarket.dto.entities.TransportModelDTO;
import com.example.securityumarket.dto.entities.WheelConfigurationDTO;
import com.example.securityumarket.dto.filters.request.UpdateFilterParametersRequest;
import com.example.securityumarket.dto.filters.response.FilterParametersResponse;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.services.jpa.BodyTypeService;
import com.example.securityumarket.services.jpa.DriveTypeService;
import com.example.securityumarket.services.jpa.FuelTypeService;
import com.example.securityumarket.services.jpa.NumberAxlesService;
import com.example.securityumarket.services.jpa.ProducingCountryService;
import com.example.securityumarket.services.jpa.TransmissionService;
import com.example.securityumarket.services.jpa.TransportBrandService;
import com.example.securityumarket.services.jpa.TransportColorService;
import com.example.securityumarket.services.jpa.TransportConditionService;
import com.example.securityumarket.services.jpa.TransportModelService;
import com.example.securityumarket.services.jpa.TransportService;
import com.example.securityumarket.services.jpa.WheelConfigurationService;
import com.example.securityumarket.services.rabbitmq.producer.FilterParametersProducer;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class FilterParametersService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final TransportService transportService;

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

    private final FilterParametersProducer filterParametersProducer;

    public void saveFilterParameters(Long transportTypeId) {
        String key = generateKey(transportTypeId);
        FilterParametersResponse filterParameters = buildFilterParametersResponse(transportTypeId);
        redisTemplate.opsForValue().set(key, filterParameters);
    }

    public FilterParametersResponse getFilterParameters(Long transportTypeId, List<Long> transportBrands) {
        String key = generateKey(transportTypeId);

        FilterParametersResponse filterParameters = (FilterParametersResponse) redisTemplate.opsForValue().get(key);

        if (filterParameters == null) {
            filterParameters = buildFilterParametersResponse(transportTypeId);
            filterParametersProducer.produce(new UpdateFilterParametersRequest(transportTypeId));
        }

        if(transportBrands != null) {
            filterParameters.setTransportModelDTOS(getTransportModelDTOS(transportTypeId, transportBrands));
        }

        return filterParameters;
    }

    private String generateKey(Long transportType) {
        return switch (transportType.intValue()) {
            case 1 -> "PassengerFilterParameters";
            case 2 -> "MotorcycleFilterParameters";
            case 3 -> "TruckFilterParameters";
            case 4 -> "SpecializedFilterParameters";
            case 5 -> "AgriculturalFilterParameters";
            case 6 -> "WaterFilterParameters";
            default -> throw new DataNotValidException("Invalid transport type");
        };
    }

    public void update(Transport transport) {
        Long transportTypeId = transport
                .getTransportModel()
                .getTransportTypeBrand()
                .getTransportType().getId();

        FilterParametersResponse filterParameters = getFilterParameters(transportTypeId, null);

        if (isFieldUpdateRequired(filterParameters, transport)) {
            filterParametersProducer.produce(
                    new UpdateFilterParametersRequest(transportTypeId));
        }
    }

    private boolean isFieldUpdateRequired(FilterParametersResponse parameters, Transport transport) {
        return isPriceUpdateRequired(parameters, transport) ||
                isMileageUpdateRequired(parameters, transport) ||
                isNumberOfDoorsUpdateRequired(parameters, transport) ||
                isNumberOfSeatsUpdateRequired(parameters, transport) ||
                isEngineDisplacementUpdateRequired(parameters, transport) ||
                isEnginePowerUpdateRequired(parameters, transport);
    }

    private boolean isPriceUpdateRequired(FilterParametersResponse parameters, Transport transport) {
        BigDecimal price = transport.getPrice();
        BigDecimal maxPrice = parameters.getPriceTo();
        BigDecimal minPrice = parameters.getPriceFrom();
        return price != null && (price.compareTo(maxPrice) > 0 || price.compareTo(minPrice) < 0);
    }

    private boolean isMileageUpdateRequired(FilterParametersResponse parameters, Transport transport) {
        Integer mileage = transport.getMileage();
        Integer maxMileage = parameters.getMileageTo();
        Integer minMileage = parameters.getMileageFrom();
        return mileage != null && (mileage > maxMileage || mileage < minMileage);
    }

    private boolean isNumberOfDoorsUpdateRequired(FilterParametersResponse parameters, Transport transport) {
        Integer numberOfDoors = transport.getNumberOfDoors();
        Integer maxNumberOfDoors = parameters.getNumberOfDoorsTo();
        Integer minNumberOfDoors = parameters.getNumberOfDoorsFrom();
        return numberOfDoors != null && (numberOfDoors > maxNumberOfDoors || numberOfDoors < minNumberOfDoors);
    }

    private boolean isNumberOfSeatsUpdateRequired(FilterParametersResponse parameters, Transport transport) {
        Integer numberOfSeats = transport.getNumberOfSeats();
        Integer maxNumberOfSeats = parameters.getNumberOfSeatsTo();
        Integer minNumberOfSeats = parameters.getNumberOfSeatsFrom();
        return numberOfSeats != null && (numberOfSeats > maxNumberOfSeats || numberOfSeats < minNumberOfSeats);
    }

    private boolean isEngineDisplacementUpdateRequired(FilterParametersResponse parameters, Transport transport) {
        Double engineDisplacement = transport.getEngineDisplacement();
        Double maxEngineDisplacement = parameters.getEngineDisplacementTo();
        Double minEngineDisplacement = parameters.getEngineDisplacementFrom();
        return engineDisplacement != null && (engineDisplacement > maxEngineDisplacement ||
                engineDisplacement < minEngineDisplacement);
    }

    private boolean isEnginePowerUpdateRequired(FilterParametersResponse parameters, Transport transport) {
        Integer enginePower = transport.getEnginePower();
        Integer maxEnginePower = parameters.getEnginePowerTo();
        Integer minEnginePower = parameters.getEnginePowerFrom();
        return enginePower != null && (enginePower > maxEnginePower || enginePower < minEnginePower);
    }

    private FilterParametersResponse buildFilterParametersResponse(
            Long transportTypeId) {

        List<Transport> transports = transportService.findAllByTransportTypeId(transportTypeId);

        FilterParametersResponse response = FilterParametersResponse.builder()
                .transportBrandDTOS(getTransportBrandDTOS(transportTypeId))
                .bodyTypeDTOS(getBodyTypeDTOS(transportTypeId))
                .producingCountryDTOS(getProducingCountryDTOS())
                .yearsFrom(getMinimalYear(transports))
                .yearsTo(getMaximalYear(transports))
                .priceFrom(getMinimalPrice(transports))
                .priceTo(getMaximalPrice(transports))
                .transportConditionDTOS(getTransportConditionDTOS())
                .transportColorDTOS(getTransportColorDTOS())
                .build();

        switch (transportTypeId.intValue()) {
            case 1 -> motorizedFourWheeledFilterStrategy(transportTypeId, response, transports);
            case 2 -> motorizedFilterStrategy(transportTypeId, response, transports);
            case 6 -> waterFilterStrategy(transportTypeId, response, transports);
            default -> loadBearingFilterStrategy(transportTypeId, response, transports);
        }

        return response;
    }

    private void loadBearingFilterStrategy(Long transportTypeId, FilterParametersResponse
            response, List<Transport> transports) {
        motorizedFourWheeledFilterStrategy(transportTypeId, response, transports);
        response.setNumberAxlesDTOS(getNumberAxlesDTOS());
        response.setWheelConfigurationDTOS(getWheelConfigurationDTOS());
    }

    private void waterFilterStrategy(Long transportTypeId, FilterParametersResponse
            response, List<Transport> transports) {
        motorizedFilterStrategy(transportTypeId, response, transports);
        response.setNumberOfSeatsFrom(getMinimalNumberOfSeats(transports));
        response.setNumberOfSeatsTo(getMaximalNumberOfSeats(transports));
        response.setTransmissionDTOS(null);
        response.setDriveTypeDTOS(null);
    }

    private void motorizedFilterStrategy(Long transportTypeId, FilterParametersResponse
            response, List<Transport> transports) {
        response.setTransmissionDTOS(getTransmissionDTOS());
        response.setFuelTypeDTOS(getFuelTypeDTOS());
        response.setEnginePowerFrom(getMinimalEnginePower(transports));
        response.setEnginePowerTo(getMaximalEnginePower(transports));
        response.setEngineDisplacementFrom(getMinimalEngineDisplacement(transports));
        response.setEngineDisplacementTo(getMaximalEngineDisplacement(transports));
        response.setDriveTypeDTOS(getDriveTypeDTOS(transportTypeId));
        response.setMileageFrom(getMinimalMileage(transports));
        response.setMileageTo(getMaximalMileage(transports));
    }

    private void motorizedFourWheeledFilterStrategy(Long transportTypeId, FilterParametersResponse
            response, List<Transport> transports) {
        motorizedFilterStrategy(transportTypeId, response, transports);
        response.setNumberOfSeatsFrom(getMinimalNumberOfSeats(transports));
        response.setNumberOfSeatsTo(getMaximalNumberOfSeats(transports));
        response.setNumberOfDoorsFrom(getMinimalNumberOfDoors(transports));
        response.setNumberOfDoorsTo(getMaximalNumberOfDoors(transports));
    }

    private Integer getMaximalNumberOfSeats(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getNumberOfSeats)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(50);
    }

    private Integer getMinimalNumberOfSeats(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getNumberOfSeats)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    private Integer getMaximalNumberOfDoors(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getNumberOfDoors)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    private Integer getMinimalNumberOfDoors(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getNumberOfDoors)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    private Double getMaximalEngineDisplacement(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getEngineDisplacement)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    private Double getMinimalEngineDisplacement(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getEngineDisplacement)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    private Integer getMaximalEnginePower(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getEnginePower)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    private Integer getMinimalEnginePower(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getEnginePower)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    private Integer getMaximalMileage(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getMileage)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    private Integer getMinimalMileage(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getMileage)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    private Integer getMaximalYear(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getYear)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    private Integer getMinimalYear(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getYear)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    private BigDecimal getMinimalPrice(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getPrice)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
    }

    private BigDecimal getMaximalPrice(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getPrice)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    private List<TransportBrandDTO> getTransportBrandDTOS(Long transportTypeId) {
        return transportBrandService.findAllSpecification(transportTypeId).stream()
                .map(transportBrand ->
                        TransportBrandDTO.builder()
                                .transportBrandId(transportBrand.getId())
                                .brand(transportBrand.getBrand())
                                .build())
                .toList();
    }

    private List<TransportModelDTO> getTransportModelDTOS(
            Long transportTypeId, List<Long> transportBrands) {

        return transportBrands.stream()
                .map(transportBrand -> TransportModelDTO.builder()
                        .brandId(transportBrand)
                        .models(getTransportModels(transportTypeId, transportBrand))
                        .build()
                ).toList();
    }

    private List<ModelDTO> getTransportModels(Long transportTypeId, Long transportBrand) {
        return transportModelService
                .findAllByTransportTypeAndBrandSpecification(transportTypeId, transportBrand).stream()
                .map(transportModel -> ModelDTO.builder()
                        .modelId(transportModel.getId())
                        .model(transportModel.getModel())
                        .build()).toList();
    }

    private List<BodyTypeDTO> getBodyTypeDTOS(Long transportTypeId) {
        return bodyTypeService.findAllByTransportTypesId(transportTypeId)
                .stream()
                .map(bodyType ->
                        BodyTypeDTO.builder()
                                .bodyTypeId(bodyType.getId())
                                .bodyType(bodyType.getBodyType())
                                .build())
                .toList();
    }

    private List<DriveTypeDTO> getDriveTypeDTOS(Long transportTypeId) {
        return driveTypeService.findAllByTransportTypesId(transportTypeId)
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
                .filter(Objects::nonNull)
                .map(wheelConfiguration ->
                        WheelConfigurationDTO.builder()
                                .wheelConfigurationId(wheelConfiguration.getId())
                                .wheelConfiguration(wheelConfiguration.getWheelConfiguration())
                                .build())
                .toList();
    }

    private List<NumberAxlesDTO> getNumberAxlesDTOS() {
        return numberAxlesService.findAll().stream()
                .filter(Objects::nonNull)
                .map(numberAxles ->
                        NumberAxlesDTO.builder()
                                .numberAxlesId(numberAxles.getId())
                                .numberAxles(numberAxles.getNumberAxles())
                                .build())
                .toList();
    }

}
