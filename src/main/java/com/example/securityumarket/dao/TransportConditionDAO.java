package com.example.securityumarket.dao;

import com.example.securityumarket.models.TransportCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportConditionDAO extends JpaRepository<TransportCondition, Long> {
}
