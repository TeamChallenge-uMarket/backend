package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportSubscriptionDAO;
import com.example.securityumarket.models.Subscription;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.TransportSubscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TransportSubscription> findAllByTransport(Transport transport) {
        return transportSubscriptionDAO.findAllByTransport(transport);
    }

}
