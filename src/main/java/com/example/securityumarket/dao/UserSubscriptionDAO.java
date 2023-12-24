package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.models.entities.UserSubscription;
import com.example.securityumarket.models.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionDAO extends JpaRepository<UserSubscription, Long> {
    Optional<UserSubscription> findBySubscriptionAndUser(Subscription subscription, Users users);

    Optional<List<UserSubscription>> findAllBySubscription(Subscription subscription);
    boolean existsBySubscription(Subscription subscription);
}
