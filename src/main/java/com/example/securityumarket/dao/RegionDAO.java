package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionDAO extends JpaRepository<Region, Long> {
    Optional<Region> findByDescription(String region);
}
