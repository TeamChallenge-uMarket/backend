package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.FuelTypeDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.FuelType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FuelTypeService {

    private final FuelTypeDAO fuelTypeDAO;

    public FuelType findById(Long fuelTypeId) {
        return fuelTypeDAO.findById(fuelTypeId)
                .orElseThrow(() -> new DataNotFoundException("Transmission by id"));
    }


    public List<FuelType> findAll() {
        return fuelTypeDAO.findAll();
    }
}
