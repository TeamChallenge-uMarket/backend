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

    public City findByRegionDescriptionAndDescription(String region, String city) {
        String address = String.format("%s from %s",city, region);
        return cityDAO.findByRegionDescriptionAndDescription(region,city)
                .orElseThrow(() -> new DataNotFoundException(address));
    }

    public List<City> findByRegion(Long regionId) {
        return cityDAO.findAllByRegionId(regionId)
                .filter(cities -> !cities.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Cities from region"));
    }
}
