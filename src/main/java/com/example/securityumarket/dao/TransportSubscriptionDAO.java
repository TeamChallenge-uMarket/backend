package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TransportSubscriptionDAO extends JpaRepository<TransportSubscription, Long> {
    boolean existsByTransport(Transport transport);
    Optional<TransportSubscription> findAllBySubscription(Subscription subscription);
}