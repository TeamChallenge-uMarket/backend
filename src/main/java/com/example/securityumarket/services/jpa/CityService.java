package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CityDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityDAO cityDAO;

    public City findByRegionDescriptionAndDescription(Long regionId, Long cityId) {
        return cityDAO.findByRegionIdAndId(regionId, cityId)
                .orElseThrow(() -> new DataNotFoundException("City"));
    }

    public List<City> findByRegion(Long regionId) {
        return cityDAO.findAllByRegionId(regionId)
                .filter(cities -> !cities.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Cities from region"));
    }

    public City findById(Long cityId) {
        return cityDAO.findById(cityId)
                .orElseThrow(() -> new DataNotFoundException("City by id"));
    }
}
