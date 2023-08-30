package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Long> {
}
