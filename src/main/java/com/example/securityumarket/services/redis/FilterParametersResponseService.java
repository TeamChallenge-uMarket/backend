package com.example.securityumarket.services.redis;

import com.example.securityumarket.dto.entities.BodyTypeDTO;
import com.example.securityumarket.dto.entities.DriveTypeDTO;
import com.example.securityumarket.dto.entities.FuelTypeDTO;
import com.example.securityumarket.dto.entities.NumberAxlesDTO;
import com.example.securityumarket.dto.entities.ProducingCountryDTO;
import com.example.securityumarket.dto.entities.TransmissionDTO;
import com.example.securityumarket.dto.entities.TransportBrandDTO;
import com.example.securityumarket.dto.entities.TransportColorDTO;
import com.example.securityumarket.dto.entities.TransportConditionDTO;
import com.example.securityumarket.dto.entities.TransportModelDTO;
import com.example.securityumarket.dto.entities.WheelConfigurationDTO;
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
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class FilterParametersResponseService {

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


    public void saveFilterParameters(Long transportTypeId, FilterParametersResponse response) {
        String key = generateKey(transportTypeId);
        redisTemplate.opsForValue().set(key, response);
    }

    public FilterParametersResponse getFilterParameters(Long transportTypeId, List<Long> transportBrands) {
        String key = generateKey(transportTypeId);

        FilterParametersResponse filterParameters = (FilterParametersResponse) redisTemplate.opsForValue().get(key);

        if (filterParameters == null) {
            filterParameters = buildFilterParametersResponse(transportTypeId);
            redisTemplate.opsForValue().set(key, filterParameters);
        }

        filterParameters.setTransportModelDTOS(getTransportModelDTOS(transportTypeId, transportBrands));
        return filterParameters;
    }

    private String generateKey(Long transportType) {
        if (transportType == 1) {
            return "PassengerFilterParameters";
        } else if (transportType == 2) {
            return "MotorcycleFilterParameters";
        } else if (transportType == 3) {
            return "TruckFilterParameters";
        } else if (transportType == 4) {
            return "SpecializedFilterParameters";
        } else if (transportType == 5) {
            return "AgriculturalFilterParameters";
        } else if (transportType == 6) {
            return "WaterFilterParameters";
        }
        throw new DataNotValidException("Invalid transport type");
    }

    private FilterParametersResponse buildFilterParametersResponse(
            Long transportTypeId) {
        List<Transport> transports = transportService.findAllByTransportTypeId(transportTypeId);
        return FilterParametersResponse.builder()
                .transportBrandDTOS(getTransportBrandDTOS(transportTypeId))
                .bodyTypeDTOS(getBodyTypeDTOS(transportTypeId))
                .driveTypeDTOS(getDriveTypeDTOS(transportTypeId))
                .fuelTypeDTOS(getFuelTypeDTOS())
                .producingCountryDTOS(getProducingCountryDTOS())
                .transmissionDTOS(getTransmissionDTOS())
                .transportColorDTOS(getTransportColorDTOS())
                .transportConditionDTOS(getTransportConditionDTOS())
                .priceFrom(getMinimalPrice(transports))
                .priceTo(getMaximalPrice(transports))
                .yearsFrom(getMinimalYear(transports))
                .yearsTo(getMaximalYear(transports))
                .mileageFrom(getMinimalMileage(transports))
                .mileageTo(getMaximalMileage(transports))
                .enginePowerFrom(getMinimalEnginePower(transports))
                .enginePowerTo(getMaximalEnginePower(transports))
                .engineDisplacementFrom(getMinimalEngineDisplacement(transports))
                .engineDisplacementTo(getMaximalEngineDisplacement(transports))
                .numberOfDoorsFrom(getMinimalNumberOfDoors(transports))
                .numberOfDoorsTo(getMaximalNumberOfDoors(transports))
                .numberOfSeatsFrom(getMinimalNumberOfSeats(transports))
                .numberOfSeatsTo(getMaximalNumberOfSeats(transports))
                .wheelConfigurationDTOS(getWheelConfigurationDTOS())
                .numberAxlesDTOS(getNumberAxlesDTOS())
                .build();
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
                .orElse(1);
    }

    private Integer getMaximalNumberOfDoors(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getNumberOfDoors)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(20);
    }

    private Integer getMinimalNumberOfDoors(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getNumberOfDoors)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(1);
    }

    private Double getMaximalEngineDisplacement(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getEngineDisplacement)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(50.0);
    }

    private Double getMinimalEngineDisplacement(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getEngineDisplacement)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(1.0);
    }

    private Integer getMaximalEnginePower(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getEnginePower)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(2000);
    }

    private Integer getMinimalEnginePower(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getEnginePower)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(1);
    }

    private Integer getMaximalMileage(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getMileage)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(999_999);
    }

    private Integer getMinimalMileage(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getMileage)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(1);
    }

    private Integer getMaximalYear(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getYear)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(LocalDate.now().getYear());
    }

    private Integer getMinimalYear(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getYear)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(1970);
    }

    private BigDecimal getMinimalPrice(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getPrice)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ONE);
    }

    private BigDecimal getMaximalPrice(List<Transport> transports) {
        return transports.stream()
                .map(Transport::getPrice)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ONE);
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
        return transportModelService
                .findAllByTransportTypeAndBrandSpecification(transportTypeId, transportBrands)
                .stream()
                .map(model ->
                        TransportModelDTO.builder()
                                .transportModelId(model.getId())
                                .model(model.getModel())
                                .build())
                .toList();
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
