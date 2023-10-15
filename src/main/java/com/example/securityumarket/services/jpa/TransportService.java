package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.main_page.request.RequestTransportSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.ResponseTransportDTO;
import com.example.securityumarket.models.DTO.transports.LoadBearingVehicleDTO;
import com.example.securityumarket.models.DTO.transports.impl.*;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.util.TransportConverter;
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
                .map(transportConverter::convertTransportToPassCarDTO)
                .collect(Collectors.toList());
    }

    public List<AgriculturalDTO> convertTransportListToAgriculturalDTOList(List<Transport> newTransports) {
        return null;//TODO
    }

    public List<SpecializedVehicleDTO> convertTransportListToSpecializedVehicleDTO(List<Transport> newTransports) {
        return null;//TODO

    }

    public List<MotorcycleDTO> convertTransportListToMotorcycleDTOList(List<Transport> newTransports) {
        return null;//TODO
    }

    public List<TruckDTO> convertTransportListToTruckDTOOList(List<Transport> newTransports) {
        return null;//TODO
    }

    public List<WaterVehicleDTO> convertTransportListToWaterVehicleDTOList(List<Transport> newTransports) {
        return null;//TODO
    }






}
