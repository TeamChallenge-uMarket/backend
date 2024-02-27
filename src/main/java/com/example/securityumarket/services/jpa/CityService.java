package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CityDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.City;
import com.example.securityumarket.dao.specifications.CitySpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityService {

    private final CityDAO cityDAO;

    public City findById(Long cityId) {
        return cityDAO.findById(cityId)
                .orElseThrow(() -> new DataNotFoundException("City by id"));
    }

    public List<City> findAllByRegionId(List<Long> regionId) {
        return cityDAO.findAll(CitySpecifications.hasRegionId(regionId));
    }

    public List<City> findAllByRegion(Long regionId) {
        return cityDAO.findAll(CitySpecifications.hasRegionId(regionId));
    }
}
