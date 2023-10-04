package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarModelDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.CarModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarModelService {

    private final CarModelDAO carModelDAO;

    public CarModel findByModel(String model) {
       return carModelDAO.findByModel(model)
                .orElseThrow(() -> new UAutoException("Model not found"));
    }

    public List<CarModel> findAllByCarBrand(long brandId) {
        return carModelDAO.findAllByCarBrand(brandId)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new UAutoException("Models not found by brands"));
    }
}
