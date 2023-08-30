package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Long> {
}
