package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDAO extends JpaRepository<Car, Long> {
}
