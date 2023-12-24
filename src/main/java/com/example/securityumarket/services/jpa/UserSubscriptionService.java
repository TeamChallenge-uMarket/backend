package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.UserSubscriptionDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.models.entities.UserSubscription;
import com.example.securityumarket.models.entities.Users;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserSubscriptionService {

    private final UserSubscriptionDAO userSubscriptionDAO;


    public void save(UserSubscription userSubscription) {
        userSubscriptionDAO.save(userSubscription);
    }

    public void save(Users user, Subscription subscription) {
        UserSubscription userSubscription = buildUserSubscription(user, subscription);
        userSubscriptionDAO.save(userSubscription);
    }

    public UserSubscription buildUserSubscription(Users user, Subscription subscription) {
        return UserSubscription.builder()
                .user(user)
                .subscription(subscription)
                .build();
    }

    public void deleteBySubscriptionAndUser(Subscription subscription, Users user) {
        UserSubscription bySubscriptionAndUser = userSubscriptionDAO.findBySubscriptionAndUser(subscription, user)
                .orElseThrow(() -> new DataNotFoundException("UserSubscription by subscription and user"));
        userSubscriptionDAO.delete(bySubscriptionAndUser);
    }

    public boolean existsBySubscription(Subscription subscription) {
        return userSubscriptionDAO.existsBySubscription(subscription);
    }

    public Optional<List<UserSubscription>> findAllBySubscription(Subscription subscription) {
        return userSubscriptionDAO.findAllBySubscription(subscription);
    }

    public Optional<List<Users>> findUsersBySubscription(Subscription subscription) {
        Optional<List<UserSubscription>> allBySubscription = findAllBySubscription(subscription);
        return allBySubscription.map(userSubscriptions -> userSubscriptions
                .stream()
                .map(UserSubscription::getUser)
                .toList());
    }
}
