package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarViewDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.Users;
import lombok.AllArgsConstructor;
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
                .orElseThrow(() -> new UAutoException("Popular cars not found by request"));
    }

    public List<Car> findViewedCarsByRegisteredUser(Users user, PageRequest of) {
        return carViewDAO.findViewedCarsByRegisteredUser(user, of)
                .orElseThrow(() -> new UAutoException("Cars not found by request"));
    }
}
