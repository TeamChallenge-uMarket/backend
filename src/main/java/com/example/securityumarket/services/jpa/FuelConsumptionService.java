package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.FuelConsumptionDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.FuelConsumption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FuelConsumptionService {

    private final FuelConsumptionDAO fuelConsumptionDAO;

    public FuelConsumption save(int consumptionCity, int consumptionHighway, int consumptionMixed) {
        if (consumptionCity > 1 && consumptionCity < 50 &&
            consumptionHighway > 1 && consumptionHighway < 50 &&
            consumptionMixed > 1 && consumptionMixed < 50) {
            FuelConsumption fuelConsumption = FuelConsumption.builder()
                    .city(consumptionCity)
                    .highway(consumptionHighway)
                    .mixed(consumptionMixed)
                    .build();
            return fuelConsumptionDAO.save(fuelConsumption);
        } else {
            throw new UAutoException("Enter the correct fuel consumption > 1 L per 100 km");
        }
    }

}
