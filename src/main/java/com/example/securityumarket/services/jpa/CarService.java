package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarDAO;
import com.example.securityumarket.dao.CarGalleryDAO;
import com.example.securityumarket.models.DTO.main_page.response.ResponseCarDTO;
import com.example.securityumarket.models.entities.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarDAO carDAO;
    private final CarGalleryDAO carGalleryDAO;

    public Car save(Car car) {
        return carDAO.save(car);
    }

    public List<ResponseCarDTO> convertCarsListToDtoCarsList(List<Car> newCars) {
        return newCars.stream()
                .map(car -> ResponseCarDTO.builder()
                        .carId(car.getId())
                        .carModel(car.getCarModel().getModel())
                        .carBrand(car.getCarModel().getCarBrand().getBrand())
                        .price(car.getPrice().getPrice())
                        .mileage(car.getMileage())
                        .year(car.getYear())
                        .city(car.getCity().getDescription())
                        .transmission(car.getTransmission())
                        .fuelType(car.getFuelType())
                        .imgUrlSmall(carGalleryDAO.findSmallMainPic(car.getId()))
                        .created(car.getCreated().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
