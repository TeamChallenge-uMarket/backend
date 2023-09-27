package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CityDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.City;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CityService {
    private final CityDAO cityDAO;

    public City findByRegionDescriptionAndDescription(String region, String city) {
        return cityDAO.findByRegionDescriptionAndDescription(region,city)
                .orElseThrow(() -> new UAutoException("Region from this city not found"));
    }
}
