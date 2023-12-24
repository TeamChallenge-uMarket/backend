package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportDAO;
import com.example.securityumarket.exception.BadRequestException;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.exception.InsufficientPermissionsException;
import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.DTO.pages.catalog.response.ResponseSearchDTO;
import com.example.securityumarket.models.DTO.transports.TransportDTO;
import com.example.securityumarket.models.DTO.transports.impl.*;
import com.example.securityumarket.models.DTO.pages.user.request.RequestUpdateTransportDetails;
import com.example.securityumarket.models.DTO.pages.user.response.TransportByStatusResponse;
import com.example.securityumarket.models.entities.*;
import com.example.securityumarket.util.converter.transposrt_type.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO.OrderBy;
import static com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO.SortBy;
import static com.example.securityumarket.models.specifications.TransportSpecifications.*;


@RequiredArgsConstructor
@Service
public class TransportService {

    private final TransportDAO transportDAO;

    private final TransportConverter transportConverter;

    private final UserService userService;

    private final BodyTypeService bodyTypeService;

    private final CityService cityService;

    private final TransmissionService transmissionService;

    private final FuelTypeService fuelTypeService;

    private final DriveTypeService driveTypeService;

    private final NumberAxlesService numberAxlesService;

    private final TransportColorService transportColorService;

    private final TransportConditionService transportConditionService;

    private final ProducingCountryService producingCountryService;

    private final WheelConfigurationService wheelConfigurationService;

    private final TransportGalleryService transportGalleryService;


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

    public ResponseEntity<List<TransportByStatusResponse>> getMyTransportsByStatus(String status) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        try {
            Transport.Status transportStatus = Transport.Status.valueOf(status.toUpperCase());
            List<Transport> transportByUserAndStatus = findTransportByUserAndStatus(authenticatedUser, transportStatus);
            return ResponseEntity.ok(convertTransportListToTransportByStatusResponse(transportByUserAndStatus));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status");
        }
    }

    @Transactional
    public ResponseEntity<String> updateTransportStatusByTransportIdAndStatus(Long transportId, String status) {
        try {
            Users authenticatedUser = userService.getAuthenticatedUser();
            Transport transportById = findTransportById(transportId);
            Transport.Status transportStatus = Transport.Status.valueOf(status.toUpperCase());

            if (isUserHasAdminOrModeratorRole(authenticatedUser)) {
                updateStatusByTransportIdAndStatus(transportById, transportStatus);
            } else if (transportStatus.equals(Transport.Status.ACTIVE)) {
                updateStatusByTransportIdAndStatus(transportById, Transport.Status.PENDING);
            } else if (isUserHasUserRole(authenticatedUser)) {
                updateStatusByTransportIdAndStatus(transportById, transportStatus);
            } else {
                throw new InsufficientPermissionsException("to update the transport status");
            }

            return ResponseEntity.ok("The status of the transport has been successfully updated");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status");
        }
    }

    private boolean isUserHasAdminOrModeratorRole(Users authenticatedUser) {
        return authenticatedUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN")
                        || authority.getAuthority().equals("MODERATOR"));
    }

    private boolean isUserHasUserRole(Users authenticatedUser) {
        return authenticatedUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("USER"));
    }

    public void updateStatusByTransportIdAndStatus(Transport transport, Transport.Status status) {
        transport.setStatus(status);
        if (status.equals(Transport.Status.ACTIVE)) {
//            subscriptionService.notifyUsers(transport);
        }
        save(transport);
    }

    public List<PassengerCarDTO> convertTransportListToPassCarDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (PassengerCarDTO) transportConverter.convertTransportToTypeDTO(
                        transport, new MotorizedFourWheeledVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<AgriculturalDTO> convertTransportListToAgriculturalDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (AgriculturalDTO) transportConverter.convertTransportToTypeDTO(
                        transport, new LoadBearingVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<SpecializedVehicleDTO> convertTransportListToSpecializedVehicleDTO(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (SpecializedVehicleDTO) transportConverter.convertTransportToTypeDTO(
                        transport, new LoadBearingVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<MotorcycleDTO> convertTransportListToMotorcycleDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (MotorcycleDTO) transportConverter.convertTransportToTypeDTO(
                        transport, new MotorizedVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<TruckDTO> convertTransportListToTruckDTOOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (TruckDTO) transportConverter.convertTransportToTypeDTO(
                        transport, new LoadBearingVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<WaterVehicleDTO> convertTransportListToWaterVehicleDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (WaterVehicleDTO) transportConverter.convertTransportToTypeDTO(
                        transport, new WaterVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<ResponseSearchDTO> convertTransportListToTransportSearchDTO(List<Transport> transports) {
        return transports.stream()
                .map(transportConverter::convertTransportTransportSearchDTO)
                .collect(Collectors.toList());
    }


    public List<TransportByStatusResponse> convertTransportListToTransportByStatusResponse(List<Transport> transports) {
        return transports.stream()
                .map(transportConverter::convertTransportToTransportByStatusResponse)
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

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteDeletedTransportsOlderThanOneMonth() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        transportDAO.deleteDeletedTransportsOlderThanOneMonth(oneMonthAgo);
    }

    @Transactional
    public ResponseEntity<String> updateTransportDetails(
            Long transportId,
            @Valid RequestUpdateTransportDetails transportDetailsDTO,
            MultipartFile[] multipartFiles) {

        Transport transport = findTransportById(transportId);

        if (transportDetailsDTO != null) {
            updateTransportFields(transportDetailsDTO, transport);
        }

        if (multipartFiles != null) {
            updateFieldIfPresent(multipartFiles, files -> {
                if (transportDetailsDTO != null) {
                    transportGalleryService.uploadFiles(
                            files, transportDetailsDTO.getMainPhoto(), transport);
                } else {
                    transportGalleryService.uploadFiles(
                            files, null, transport);
                }
            });
        }

        if (transport.getStatus().equals(Transport.Status.ACTIVE)) {
            transport.setStatus(Transport.Status.PENDING);
        }

        save(transport);
        return ResponseEntity.ok("Transport details updated successfully");
    }

    private <T> void updateFieldIfPresent(T newValue, Consumer<T> updateFunction) {
        Optional.ofNullable(newValue)
                .ifPresent(updateFunction);
    }

    private void updateTransportFields(
            RequestUpdateTransportDetails transportDetailsDTO, Transport currentTransport) {
        updateFieldIfPresent(transportDetailsDTO.getYear(), currentTransport::setYear);

        updateFieldIfPresent(transportDetailsDTO.getMileage(), currentTransport::setMileage);

        updateFieldIfPresent(transportDetailsDTO.getPrice(), currentTransport::setPrice);

        updateFieldIfPresent(transportDetailsDTO.getBargain(), currentTransport::setBargain);

        updateFieldIfPresent(transportDetailsDTO.getTrade(), currentTransport::setTrade);

        updateFieldIfPresent(transportDetailsDTO.getMilitary(), currentTransport::setMilitary);

        updateFieldIfPresent(transportDetailsDTO.getInstallmentPayment(), currentTransport::setInstallmentPayment);

        updateFieldIfPresent(transportDetailsDTO.getUncleared(), currentTransport::setUncleared);

        updateFieldIfPresent(transportDetailsDTO.getLoadCapacity(), currentTransport::setLoadCapacity);

        updateFieldIfPresent(transportDetailsDTO.getAccidentHistory(), currentTransport::setAccidentHistory);

        updateFieldIfPresent(transportDetailsDTO.getNumberOfDoors(), currentTransport::setNumberOfDoors);

        updateFieldIfPresent(transportDetailsDTO.getNumberOfSeats(), currentTransport::setNumberOfSeats);

        updateFieldIfPresent(transportDetailsDTO.getConsumptionCity(), currentTransport::setFuelConsumptionCity);

        updateFieldIfPresent(transportDetailsDTO.getConsumptionHighway(), currentTransport::setFuelConsumptionHighway);

        updateFieldIfPresent(transportDetailsDTO.getConsumptionMixed(), currentTransport::setFuelConsumptionMixed);

        updateFieldIfPresent(transportDetailsDTO.getEngineDisplacement(), currentTransport::setEngineDisplacement);

        updateFieldIfPresent(transportDetailsDTO.getEnginePower(), currentTransport::setEnginePower);

        updateFieldIfPresent(transportDetailsDTO.getVincode(), currentTransport::setVincode);

        updateFieldIfPresent(transportDetailsDTO.getDescription(), currentTransport::setDescription);


        updateFieldIfPresent(transportDetailsDTO.getBodyType(), bodyType -> {
            bodyTypeService.findById(bodyType);
            currentTransport.setId(bodyType);
        });

        updateFieldIfPresent(transportDetailsDTO.getCity(), cityId -> {
            City city = cityService.findById(cityId);
            currentTransport.setCity(city);
        });

        updateFieldIfPresent(transportDetailsDTO.getTransmission(), transmissionId -> {
            Transmission transmission = transmissionService.findById(transmissionId);
            currentTransport.setTransmission(transmission);
        });

        updateFieldIfPresent(transportDetailsDTO.getFuelType(), fuelTypeId -> {
            FuelType fuelType = fuelTypeService.findById(fuelTypeId);
            currentTransport.setFuelType(fuelType);
        });

        updateFieldIfPresent(transportDetailsDTO.getDriveType(), driveTypeId -> {
            DriveType driveType = driveTypeService.findById(driveTypeId);
            currentTransport.setDriveType(driveType);
        });

        updateFieldIfPresent(transportDetailsDTO.getNumberAxles(), numberAxlesId -> {
            NumberAxles numberAxles = numberAxlesService.findById(numberAxlesId);
            currentTransport.setNumberAxles(numberAxles);
        });

        updateFieldIfPresent(transportDetailsDTO.getColor(), colorId -> {
            TransportColor color = transportColorService.findById(colorId);
            currentTransport.setTransportColor(color);
        });

        updateFieldIfPresent(transportDetailsDTO.getCondition(), conditionId -> {
            TransportCondition condition = transportConditionService.findById(conditionId);
            currentTransport.setTransportCondition(condition);
        });

        updateFieldIfPresent(transportDetailsDTO.getProducingCountry(), countryId -> {
            ProducingCountry country = producingCountryService.findById(countryId);
            currentTransport.setProducingCountry(country);
        });

        updateFieldIfPresent(transportDetailsDTO.getWheelConfiguration(), wheelConfigurationId -> {
            WheelConfiguration wheelConfiguration = wheelConfigurationService.findById(wheelConfigurationId);
            currentTransport.setWheelConfiguration(wheelConfiguration);
        });
    }


    public ResponseEntity<? extends TransportDTO> getTransportDetails(Long transportId) {
        Transport transport = findTransportById(transportId);
        return convertTransportToTypeDTO(transport);
    }

    private ResponseEntity<? extends TransportDTO> convertTransportToTypeDTO(Transport transport) {
        Long transportTypeId = transport.getTransportModel().getTransportTypeBrand().getTransportType().getId();
        return switch (transportTypeId.intValue()) {
            case 1 -> ResponseEntity.ok(transportConverter.convertTransportToTypeDTO(
                    transport, new MotorizedFourWheeledVehicleConversionStrategy()));
            case 2 -> ResponseEntity.ok(transportConverter.convertTransportToTypeDTO(
                    transport, new MotorizedVehicleConversionStrategy()));
            case 3, 4, 5 -> ResponseEntity.ok(transportConverter.convertTransportToTypeDTO(
                    transport, new LoadBearingVehicleConversionStrategy()));
            case 6 -> ResponseEntity.ok(transportConverter.convertTransportToTypeDTO(
                    transport, new WaterVehicleConversionStrategy()));
            default -> throw new DataNotFoundException("Transport type id");
        };
    }
}