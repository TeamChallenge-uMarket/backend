package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.catalog.MotorcycleFilterDTO;
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
                        .imgUrl(transportGalleryService.findMainFileByTransport(car.getId()))
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
                .map(transport -> (PassengerCarDTO) transportConverter.convertTransport(transport, new MotorizedFourWheeledVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }



    public List<AgriculturalDTO> convertTransportListToAgriculturalDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (AgriculturalDTO) transportConverter.convertTransport(transport, new LoadBearingVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<SpecializedVehicleDTO> convertTransportListToSpecializedVehicleDTO(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (SpecializedVehicleDTO) transportConverter.convertTransport(transport, new LoadBearingVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<MotorcycleDTO> convertTransportListToMotorcycleDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (MotorcycleDTO) transportConverter.convertTransport(transport, new MotorizedVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<TruckDTO> convertTransportListToTruckDTOOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (TruckDTO) transportConverter.convertTransport(transport, new LoadBearingVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<WaterVehicleDTO> convertTransportListToWaterVehicleDTOList(List<Transport> newTransports) {
        return newTransports.stream()
                .map(transport -> (WaterVehicleDTO) transportConverter.convertTransport(transport, new WaterVehicleConversionStrategy()))
                .collect(Collectors.toList());
    }

    public List<Transport> findMotorcyclesByFilter(MotorcycleFilterDTO motorcycleFilterDTO, PageRequest pageRequest) {
        return transportDAO.findMotorCyclesByFilter(motorcycleFilterDTO, pageRequest)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Motorcycles"));
    }
}
