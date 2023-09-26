package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarDAO;
import com.example.securityumarket.models.entities.Car;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CarService {
    private final CarDAO carDAO;

    public Car save(Car car) {
        return carDAO.save(car);
    }
}
