package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.SubscriptionDAO;
import com.example.securityumarket.dto.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.Subscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionDAO subscriptionDAO;

    public Optional<Subscription> findByParameters (RequestSearchDTO requestSearchDTO) {
        return subscriptionDAO.findByParameters(requestSearchDTO);
    }


    public void save(Subscription subscription) {
        subscriptionDAO.save(subscription);
    }

    public Optional<Subscription> findById(Long subscriptionId) {
        return subscriptionDAO.findById(subscriptionId);
    }

    public void delete(Subscription subscription) {
        subscriptionDAO.delete(subscription);
    }

    public List<Subscription> findAll() {
        return subscriptionDAO.findAll();
    }
}
