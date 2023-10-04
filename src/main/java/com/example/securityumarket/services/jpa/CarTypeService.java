package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarTypeDAO;
import com.example.securityumarket.models.entities.CarType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarTypeService {

    private final CarTypeDAO carTypeDAO;

    public List<CarType> findAll() {
        return carTypeDAO.findAll();
    }
}
