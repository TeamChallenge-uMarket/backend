package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarViewDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarViewService {

    private final CarViewDAO carViewDAO;

    public List<Car> findPopularCars(PageRequest of) {
        return carViewDAO.findPopularCars(of)
                .orElseThrow(() -> new DataNotFoundException("Popular cars"));
    }

    public List<Car> findViewedCarsByRegisteredUser(Users user, PageRequest of) {
        return carViewDAO.findViewedCarsByRegisteredUser(user, of)
                .orElseThrow(() -> new DataNotFoundException("The most popular cars"));
    }
}
