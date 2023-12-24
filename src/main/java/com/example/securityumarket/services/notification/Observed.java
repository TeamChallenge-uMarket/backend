package com.example.securityumarket.services.notification;

import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.models.entities.Transport;

public interface Observed {

    void addSubscription(RequestSearchDTO requestSearchDTO);
    void removeSubscription(Long SubscriptionId);

    void addTransport(Transport transport);

    void removeTransport(Transport transport);

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Subscription subscription, Transport transport);
}
