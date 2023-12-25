package com.example.securityumarket.dao;

import com.example.securityumarket.models.TransportReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportReviewDAO extends JpaRepository<TransportReview, Long> {
}
