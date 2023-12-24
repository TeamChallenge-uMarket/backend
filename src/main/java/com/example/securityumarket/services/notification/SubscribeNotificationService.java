package com.example.securityumarket.services.notification;

import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.services.jpa.SubscriptionService;
import com.example.securityumarket.services.jpa.TransportSubscriptionService;
import com.example.securityumarket.services.jpa.UserSubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SubscribeNotificationService implements Observed{
    private final SubscriptionService subscriptionService;
    private final UserSubscriptionService userSubscriptionService;
    private final TransportSubscriptionService transportSubscriptionService;

    public void addSubscription(Subscription subscription) {
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {
        for (Observer observer : "підписники") {
            observer.handleEven("Список вакансій");
        }
    }
}
