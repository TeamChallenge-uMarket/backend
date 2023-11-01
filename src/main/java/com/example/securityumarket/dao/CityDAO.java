package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.City;
import com.example.securityumarket.models.entities.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityDAO extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

    Optional<City> findByRegionIdAndId (Long regionId, Long cityId);

    Optional<List<City>> findAllByRegionId(Long regionId);
}
