package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.main_page.request.RequestTransportSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.ResponseTransportDTO;
import com.example.securityumarket.models.entities.Transport;
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
}
