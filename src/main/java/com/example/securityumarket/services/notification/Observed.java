package com.example.securityumarket.services.notification;

import com.example.securityumarket.dto.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.dto.pages.subscription.SubscriptionRequest;
import com.example.securityumarket.models.Subscription;
import com.example.securityumarket.models.Transport;

public interface Observed {

    void addSubscription(RequestSearchDTO requestSearchDTO, SubscriptionRequest subscriptionRequest);
    void removeSubscription(Long SubscriptionId);

    void addTransport(Transport transport);

    void removeTransport(Transport transport);

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Subscription subscription, Transport transport);
}
