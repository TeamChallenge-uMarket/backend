package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarBrandDAO;
import com.example.securityumarket.models.entities.CarBrand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarBrandService {
    private final CarBrandDAO carBrandDAO;

    public List<CarBrand> findAll() {
        return carBrandDAO.findAll();
    }
}
