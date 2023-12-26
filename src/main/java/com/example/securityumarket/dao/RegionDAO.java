package com.example.securityumarket.dao;

import com.example.securityumarket.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionDAO extends JpaRepository<Region, Long> {
}
