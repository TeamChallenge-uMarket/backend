package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.main_page.request.RequestCarSearchDTO;
import com.example.securityumarket.models.DTO.main_page.response.ResponseCarDTO;
import com.example.securityumarket.models.entities.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CarService {

    private final CarDAO carDAO;

    private final CarGalleryService carGalleryService;


    public Car save(Car car) {
        return carDAO.save(car);
    }



    public List<Car> findNewCars(PageRequest pageRequest) {
        return carDAO.findNewCars(pageRequest)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("New cars"));
    }

    public List<Car> findCarsByRequest(RequestCarSearchDTO requestSearch, PageRequest of) {
        return carDAO.findCarsByRequest(requestSearch, of)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Cars"));
    }

    public List<ResponseCarDTO> convertCarsListToDtoCarsList(List<Car> newCars) {
        return newCars.stream()
                .map(car -> ResponseCarDTO.builder()
                        .carId(car.getId())
                        .carModel(car.getCarModel().getModel())
                        .carBrand(car.getCarModel().getCarBrand().getBrand())
                        .price(car.getPrice())
                        .mileage(car.getMileage())
                        .year(car.getYear())
                        .city(car.getCity().getDescription())
                        .transmission(car.getTransmission())
                        .fuelType(car.getFuelType())
                        .imgUrl(carGalleryService.findMainFileByCar(car.getId()))
                        .created(car.getCreated().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
