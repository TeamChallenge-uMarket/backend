package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityDAO extends JpaRepository<City, Long> {

    Optional<City> findByRegionIdAndId (Long regionId, Long cityId);

    Optional<List<City>> findAllByRegionId(Long regionId);
}
