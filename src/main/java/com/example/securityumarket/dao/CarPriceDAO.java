package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.CarPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPriceDAO extends JpaRepository<CarPrice, Long> {
}
