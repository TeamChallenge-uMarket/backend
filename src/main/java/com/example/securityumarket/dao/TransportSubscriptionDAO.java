package com.example.securityumarket.dao;

import com.example.securityumarket.models.Subscription;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.TransportSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransportSubscriptionDAO extends JpaRepository<TransportSubscription, Long> {
    List<TransportSubscription> findAllByTransport(Transport transport);
    List<TransportSubscription> findAllBySubscription(Subscription subscription);
}
