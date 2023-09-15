package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBrandDAO extends JpaRepository<CarBrand, Long> {
}
