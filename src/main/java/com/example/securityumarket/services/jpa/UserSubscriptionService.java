package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UserSubscriptionDAO;
import com.example.securityumarket.dto.pages.subscription.SubscriptionRequest;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.Subscription;
import com.example.securityumarket.models.UserSubscription;
import com.example.securityumarket.models.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserSubscriptionService {

    private final UserSubscriptionDAO userSubscriptionDAO;


    public void save(UserSubscription userSubscription) {
        userSubscriptionDAO.save(userSubscription);
    }

    public void save(Users user, Subscription subscription, SubscriptionRequest subscriptionRequest) {
        UserSubscription userSubscription = buildUserSubscription(user, subscription, subscriptionRequest);
        userSubscriptionDAO.save(userSubscription);
    }

    public Optional<UserSubscription> findBySubscriptionAndUser(Subscription subscription, Users user) {
        return userSubscriptionDAO.findBySubscriptionAndUser(subscription, user);
    }

    public UserSubscription buildUserSubscription(
            Users user, Subscription subscription, SubscriptionRequest subscriptionRequest) {
        return UserSubscription.builder()
                .user(user)
                .subscription(subscription)
                .name(subscriptionRequest.name())
                .notificationEnabled(subscriptionRequest.notificationEnabled())
                .build();
    }

    public void deleteBySubscriptionAndUser(Subscription subscription, Users user) {
        UserSubscription bySubscriptionAndUser = findBySubscriptionAndUser(subscription, user)
                .orElseThrow(() -> new DataNotFoundException("UserSubscription by subscription and user"));
        userSubscriptionDAO.delete(bySubscriptionAndUser);
    }

    public boolean existsBySubscription(Subscription subscription) {
        return userSubscriptionDAO.existsBySubscription(subscription);
    }

    public Optional<List<UserSubscription>> findAllBySubscription(Subscription subscription) {
        return userSubscriptionDAO.findAllBySubscription(subscription);
    }

    public List<UserSubscription> findAllByUser(Users authenticatedUser) {
        return userSubscriptionDAO.findAllByUser(authenticatedUser);
    }
}
