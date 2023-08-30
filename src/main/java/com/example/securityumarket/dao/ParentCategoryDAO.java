package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentCategoryDAO extends JpaRepository<ParentCategory, Long> {
}
