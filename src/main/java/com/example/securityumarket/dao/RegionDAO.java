package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionDAO extends JpaRepository<Region, Long> {
}
