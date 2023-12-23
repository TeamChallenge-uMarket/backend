package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDAO extends JpaRepository<Subscription, Long> {
}