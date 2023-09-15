package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.FuelConsumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelConsumptionDAO extends JpaRepository<FuelConsumption, Long> {
}
