package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.catalog_page.request.RequestSearchDTO;
import com.example.securityumarket.models.DTO.catalog_page.response.ResponseSearchDTO;
import com.example.securityumarket.models.DTO.main_page.request.RequestTransportSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.ResponseTransportDTO;
import com.example.securityumarket.models.DTO.transports.impl.*;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.util.converter.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.securityumarket.models.repositories.TransportSpecifications.*;


@RequiredArgsConstructor
@Service
public class TransportService {

    private final TransportDAO transportDAO;

    private final TransportGalleryService transportGalleryService;

    private final TransportConverter transportConverter;


    public Transport save(Transport transport) {
        return transportDAO.save(transport);
    }



    public List<Transport> findNewCars(PageRequest pageRequest) {
        return transportDAO.findNewCars(pageRequest)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("New transports"));
    }

    public List<Transport> findTransportByRequest(RequestTransportSearchDTO requestSearch, PageRequest of) {
        return transportDAO.findTransportByRequest(requestSearch, of)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Transports"));
    }

    public List<ResponseTransportDTO> convertCarsListToDtoCarsList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(car -> ResponseTransportDTO.builder()
                        .transportId(car.getId())
                        .transportModel(car.getTransportModel().getModel())
                        .transportBrand(car.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand())
                        .price(car.getPrice())
                        .mileage(car.getMileage())
                        .year(car.getYear())
                        .region(car.getCity().getRegion().getDescription())
                        .transmission(car.getTransmission())
                        .fuelType(car.getFuelType())
//                        .imgUrl(transportGalleryService.findMainFileByTransport(car.getId()))
                        .imgUrl("https://res.cloudinary.com/de4bysqtm/image/upload/f_auto,q_auto/l52tzjkitkoy64ttdkmx")
                        .created(car.getCreated().toString())
                        .build())
                .collect(Collectors.toList());
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

    public List<Transport> findTransportByParam(RequestSearchDTO requestSearchDTO) {
        return transportDAO.findAll(
                hasTransportType(requestSearchDTO.getTransportType())
                        .and(hasTransportType(requestSearchDTO.getTransportType()))
                        .and(hasTransportBrand(requestSearchDTO.getBrand()))
                        .and(hasModelIn(requestSearchDTO.getModels()))
                        .and(yearFrom(requestSearchDTO.getYearsFrom()))
                        .and(yearTo(requestSearchDTO.getYearsTo()))
                        .and(hasRegionIn(requestSearchDTO.getRegions()))
                        .and(hasCityIn(requestSearchDTO.getCities()))
                        .and(priceFrom(requestSearchDTO.getPriceFrom()))
                        .and(priceTo(requestSearchDTO.getPriceTo()))
                        .and(hasBodyTypeIn(requestSearchDTO.getBodyTypes()))
                        .and(hasFuelTypeIn(requestSearchDTO.getFuelTypes()))
                        .and(hasTransmissionIn(requestSearchDTO.getTransmissions()))
                        .and(hasColorIn(requestSearchDTO.getColors()))
                        .and(hasConditionIn(requestSearchDTO.getConditions()))
                        .and(hasDriveType(requestSearchDTO.getDriveTypes()))
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
                        .and(hasTrade(requestSearchDTO.getTrade()))
                        .and(hasMilitary(requestSearchDTO.getMilitary()))
                        .and(hasUncleared(requestSearchDTO.getUncleared()))
                        .and(hasBargain(requestSearchDTO.getBargain()))
                        .and(hasInstallmentPayment(requestSearchDTO.getInstallmentPayment()))
        );
    }
}
