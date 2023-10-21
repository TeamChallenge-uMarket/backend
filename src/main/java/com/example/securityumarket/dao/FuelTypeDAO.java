package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeDAO  extends JpaRepository<FuelType, Long> {
}
