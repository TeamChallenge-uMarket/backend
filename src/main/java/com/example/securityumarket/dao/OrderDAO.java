package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order, Long> {
}
