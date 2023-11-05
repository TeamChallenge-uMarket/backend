package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportDAO;
import com.example.securityumarket.exception.BadRequestException;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.catalog_page.request.RequestSearchDTO;
import com.example.securityumarket.models.DTO.catalog_page.response.ResponseSearchDTO;
import com.example.securityumarket.models.DTO.transports.impl.*;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.util.converter.transposrt_type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.securityumarket.models.DTO.catalog_page.request.RequestSearchDTO.OrderBy;
import static com.example.securityumarket.models.DTO.catalog_page.request.RequestSearchDTO.SortBy;
import static com.example.securityumarket.models.specifications.TransportSpecifications.*;


@RequiredArgsConstructor
@Service
public class TransportService {

    private final TransportDAO transportDAO;

    private final TransportGalleryService transportGalleryService;

    private final TransportConverter transportConverter;

    private final UserService userService;


    public void save(Transport transport) {
        transportDAO.save(transport);
    }


    public List<Transport> findNewTransports() {
        Pageable pageable = PageRequest.of(0, 20);
        Specification<Transport> specification = isActive()
                .and(sortBy(Transport.class, SortBy.DESC, OrderBy.CREATED));
        Page<Transport> page = transportDAO.findAll(specification, pageable);
        return page.getContent();
    }

    public List<Transport> findPopularTransport() {
        Pageable pageable = PageRequest.of(0, 54);
        Specification<Transport> specification = isActive()
                .and(sortPopularTransports());
        Page<Transport> page = transportDAO.findAll(specification, pageable);
        return page.getContent();
    }

    public List<Transport> findViewedTransportsByRegisteredUser(Users user) {
        Pageable pageable = PageRequest.of(0, 20);
        Specification<Transport> specification = isActive()
                .and(findTransportViewedByUser(user));
        Page<Transport> page = transportDAO.findAll(specification, pageable);
        return page.getContent();
    }

    public List<Transport> findFavoriteTransportsByRegisteredUser(Users user) {
        Specification<Transport> specification = isActive()
                .and(findFavoriteTransportsByUser(user))
                .and(sortBy(Transport.class, SortBy.DESC, OrderBy.CREATED));
        return transportDAO.findAll(specification);
    }

    public List<Transport> findTransportByUserAndStatus(Users user, Transport.Status status) { //TODO Change to PageRequest
        Specification<Transport> specification = findByUser(user)
                .and(hasStatus(status));
        return transportDAO.findAll(specification);
    }

    public Transport findTransportById(long carId) {
        return transportDAO.findById(carId)
                .orElseThrow(() -> new DataNotFoundException("Transport by id"));
    }

    public List<PassengerCarDTO> convertTransportListToPassCarDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (PassengerCarDTO) transportConverter.convertTransportToTypeDTO(transport, new MotorizedFourWheeledVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public ResponseEntity<List<ResponseSearchDTO>> getMyTransportsByStatus(String status) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        try {
            Transport.Status transportStatus = Transport.Status.valueOf(status.toUpperCase());
            List<Transport> transportByUserAndStatus = findTransportByUserAndStatus(authenticatedUser, transportStatus);
            return ResponseEntity.ok(convertTransportListToTransportSearchDTO(transportByUserAndStatus));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status");
        }
    }

    public List<AgriculturalDTO> convertTransportListToAgriculturalDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (AgriculturalDTO) transportConverter.convertTransportToTypeDTO(transport, new LoadBearingVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<SpecializedVehicleDTO> convertTransportListToSpecializedVehicleDTO(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (SpecializedVehicleDTO) transportConverter.convertTransportToTypeDTO(transport, new LoadBearingVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<MotorcycleDTO> convertTransportListToMotorcycleDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (MotorcycleDTO) transportConverter.convertTransportToTypeDTO(transport, new MotorizedVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<TruckDTO> convertTransportListToTruckDTOOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (TruckDTO) transportConverter.convertTransportToTypeDTO(transport, new LoadBearingVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<WaterVehicleDTO> convertTransportListToWaterVehicleDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (WaterVehicleDTO) transportConverter.convertTransportToTypeDTO(transport, new WaterVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<ResponseSearchDTO> convertTransportListToTransportSearchDTO(List<Transport> transports) {
        return transports.stream()
                .map(transportConverter::convertTransportTransportSearchDTO)
                .collect(Collectors.toList());
    }

    public List<Transport> findTransportByParam(RequestSearchDTO requestSearchDTO, PageRequest pageRequest) {
        Page<Transport> transportPage = transportDAO.findAll(
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
                , pageRequest);
        return transportPage.getContent();
    }
}
