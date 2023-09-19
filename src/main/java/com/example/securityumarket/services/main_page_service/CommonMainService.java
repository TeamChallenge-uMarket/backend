package com.example.securityumarket.services.main_page_service;

import com.example.securityumarket.dao.CarGalleryDAO;
import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.models.DTO.main_page.ResponseCarDTO;
import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonMainService {

    private final CarGalleryDAO carGalleryDAO;
    private final UsersDAO usersDAO;

    public List<ResponseCarDTO> convertCarsListToDtoCarsList(List<Car> newCars) {
       return newCars.stream()
                .map(car -> ResponseCarDTO.builder()
                        .carId(car.getId())
                        .carModel(car.getCarModel())
                        .carBrand(car.getCarModel().getCarBrand())
                        .price(car.getPrice().getPrice())
                        .mileage(car.getMileage())
                        .year(car.getYear())
                        .city(car.getCity())
                        .transmission(car.getTransmission())
                        .fuelType(car.getFuelType())
                        .imgUrlSmall(carGalleryDAO.findSmallMainPic(car.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    protected Users getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersDAO.findAppUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not founded"));
    }
}
