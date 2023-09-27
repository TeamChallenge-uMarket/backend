package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityDAO extends JpaRepository<City, Long> {
    Optional<City> findByDescription(String city);
    Optional<City> findByRegionDescriptionAndDescription(String region, String city);
}
