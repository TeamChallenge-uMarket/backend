package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Category;
import com.example.securityumarket.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Long> {
    List<Category> findAllByParentCategoryId(Long id);
}
