package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Product;
import com.example.securityumarket.models.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDAO extends JpaRepository<Product, Long> {
    List<Product> findAllByUserId(Long id);
}
