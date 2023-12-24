package com.example.securityumarket.dao;

import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionDAO extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByParameters(RequestSearchDTO requestSearchDTO);
}