package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportSubscriptionDAO;
import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportSubscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransportSubscriptionService {
    private final TransportSubscriptionDAO transportSubscriptionDAO;

    public void save(TransportSubscription transportSubscription) {
        transportSubscriptionDAO.save(transportSubscription);
    }

    public void save(Transport transport, Subscription subscription) {
        transportSubscriptionDAO.save(TransportSubscription.builder()
                .transport(transport)
                .subscription(subscription)
                .build());
    }

    public void delete(TransportSubscription transportSubscription) {
        transportSubscriptionDAO.delete(transportSubscription);
    }
}
