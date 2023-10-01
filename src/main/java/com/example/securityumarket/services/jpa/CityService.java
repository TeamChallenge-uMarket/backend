package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CityDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityDAO cityDAO;

    public City findByRegionDescriptionAndDescription(String region, String city) {
        return cityDAO.findByRegionDescriptionAndDescription(region,city)
                .orElseThrow(() -> new UAutoException("City from this region not found"));
    }

    public City findByDescription(String city) {
        return cityDAO.findByDescription(city)
                .orElseThrow(() -> new UAutoException("City not found"));
    }


    public List<City> findAll() {
        return cityDAO.findAll();
    }
}
