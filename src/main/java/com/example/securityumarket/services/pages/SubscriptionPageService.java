package com.example.securityumarket.services.pages;

import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.UserSubscription;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.*;
import com.example.securityumarket.services.notification.Observed;
import com.example.securityumarket.services.notification.Observer;
import com.example.securityumarket.util.EmailUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SubscriptionPageService implements Observed {

    private final UserService userService;
    private final CatalogPageService catalogPageService;
    private final TransportService transportService;
    private final SubscriptionService subscriptionService;
    private final UserSubscriptionService userSubscriptionService;
    private final TransportSubscriptionService transportSubscriptionService;
    private final EmailUtil emailUtil;


    @Transactional
    @Override
    public void addSubscription(RequestSearchDTO requestSearchDTO) {
        Subscription subscription = subscriptionService
                .findByParameters(requestSearchDTO).orElseGet(() ->
                        buildSubscriptionByRequestSearchDTO(requestSearchDTO));

        if (subscriptionService.findByParameters(subscription.getParameters()).isEmpty()) {
            subscriptionService.save(subscription);
        }

        saveTransportSubscription(subscription);
        saveUserSubscription(subscription);
    }

    @Transactional
    @Override
    public void removeSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionService.findById(subscriptionId)
                .orElseThrow(() -> new DataNotFoundException("Subscription by id"));

        Users authenticatedUser = userService.getAuthenticatedUser();
        userSubscriptionService.deleteBySubscriptionAndUser(subscription, authenticatedUser);

        if (!userSubscriptionService.existsBySubscription(subscription)) {
            subscriptionService.delete(subscription);
        }
    }

    @Override
    public void addTransport(Transport transport) {
        List<Subscription> all = subscriptionService.findAll();
        for (Subscription subscription : all) {
            Specification<Transport> specificationParam = catalogPageService.getSpecificationParam(subscription.getParameters());
            List<Transport> transports = transportService.findAll(specificationParam);
            if (transports.contains(transport)) {
                notifyObservers(subscription, transport);
            }
        }
    }

    @Override
    public void removeTransport(Transport transport) {

    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers(Subscription subscription, Transport transport) {
        Optional<List<UserSubscription>> allBySubscription = userSubscriptionService.findAllBySubscription(subscription);
        if (allBySubscription.isPresent()){
            List<UserSubscription> userSubscriptions = allBySubscription.get();
            List<Users> list = userSubscriptions.stream().map(UserSubscription::getUser).toList();
            emailUtil.sendNotification(list, transport);
        }
    }


    private void saveUserSubscription(Subscription subscription) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        userSubscriptionService.save(authenticatedUser, subscription);
    }

    private void saveTransportSubscription(Subscription subscription) {
        Specification<Transport> specificationParam = catalogPageService.getSpecificationParam(subscription.getParameters());
        List<Transport> transports = transportService.findAll(specificationParam);
        for (Transport transport : transports) {
            transportSubscriptionService.save(transport, subscription);
        }
    }

    private Subscription buildSubscriptionByRequestSearchDTO(RequestSearchDTO requestSearchDTO) {
        return Subscription.builder()
                .parameters(requestSearchDTO)
                .build();
    }
}
