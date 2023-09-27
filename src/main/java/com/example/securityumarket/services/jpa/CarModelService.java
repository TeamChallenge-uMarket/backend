package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarModelDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.CarModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CarModelService {

    private final CarModelDAO carModelDAO;

    public CarModel findByModel(String model) {
       return carModelDAO.findByModel(model)
                .orElseThrow(() -> new UAutoException("Model not found"));
    }
}
