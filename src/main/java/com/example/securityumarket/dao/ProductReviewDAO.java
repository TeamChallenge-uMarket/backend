package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewDAO extends JpaRepository<ProductReview, Long> {
}
