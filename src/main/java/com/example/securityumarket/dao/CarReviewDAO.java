package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.CarReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarReviewDAO extends JpaRepository<CarReview, Long> {
}
