package com.example.securityumarket.services;

import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.services.jpa.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {

    private final SubscriptionService subscriptionService;

    public void notifyUsers(Transport transport) {
        subscriptionService.notifyUsers(transport);
    }
}
