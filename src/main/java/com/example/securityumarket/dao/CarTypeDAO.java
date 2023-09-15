package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarTypeDAO extends JpaRepository<CarType, Long> {
}
