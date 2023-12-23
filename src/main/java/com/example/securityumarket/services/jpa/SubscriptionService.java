package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.SubscriptionDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.models.entities.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionDAO subscriptionDAO;
    private final UserSubscriptionService userSubscriptionService;


    @Transactional
    public void addSubscription(RequestSearchDTO requestSearchDTO, Users user) {
        Subscription subscription = buildSubscriptionByRequestSearchDTO(requestSearchDTO);
        save(subscription);
        userSubscriptionService.save(user, subscription);
    }

    @Transactional
    public void deleteSubscription(Long subscriptionId, Users user) {
        Subscription subscription = subscriptionDAO.findById(subscriptionId)
                .orElseThrow(() -> new DataNotFoundException("Subscription by id"));

        userSubscriptionService.deleteBySubscriptionAndUser(subscription, user);

        if (!userSubscriptionService.existsBySubscription(subscription)) {
            subscriptionDAO.delete(subscription);
        }
    }

    public void save(Subscription subscription) {
        subscriptionDAO.save(subscription);
    }

    private Subscription buildSubscriptionByRequestSearchDTO(RequestSearchDTO requestSearchDTO) {
        return Subscription.builder()
                    .parameters(requestSearchDTO)
                    .build();
    }
}
