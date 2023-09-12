package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelDAO extends JpaRepository<CarModel, Long> {
}
