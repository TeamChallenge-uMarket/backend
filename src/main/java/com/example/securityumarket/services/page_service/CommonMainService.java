package com.example.securityumarket.services.page_service;

import com.example.securityumarket.dao.CarGalleryDAO;
import com.example.securityumarket.dao.UsersDAO;
import com.example.securityumarket.models.DTO.main_page.response.ResponseCarDTO;
import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    protected Users getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usersDAO.findAppUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user not founded"));
    }
}