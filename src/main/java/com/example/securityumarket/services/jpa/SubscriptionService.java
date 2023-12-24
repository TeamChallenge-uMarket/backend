package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.SubscriptionDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.Subscription;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportSubscription;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.util.EmailUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionDAO subscriptionDAO;
    private final UserSubscriptionService userSubscriptionService;
    private final TransportSubscriptionService transportSubscriptionService;
    private final TransportService transportService;
    private final EmailUtil emailUtil;

    @Transactional
    public void addSubscription(RequestSearchDTO requestSearchDTO, Users user) {
        Subscription subscription = findByParameters(requestSearchDTO).orElseGet(() -> buildSubscriptionByRequestSearchDTO(requestSearchDTO));
        if (subscriptionDAO.findByParameters(subscription.getParameters()).isEmpty()) {
            save(subscription);
            List<Transport> transportByParam = transportService.findTransportByParam(subscription.getParameters(), PageRequest.of(0, Integer.MAX_VALUE));
            for (Transport transport : transportByParam) {
                transportSubscriptionService.save(transport, subscription);
            }
        }
        userSubscriptionService.save(user, subscription);
    }

    public Optional<Subscription> findByParameters (RequestSearchDTO requestSearchDTO) {
        return subscriptionDAO.findByParameters(requestSearchDTO);
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


    public void notifyUsers(Transport transport) {
        findAllByTransport(transport)
                .ifPresent(subscriptions -> subscriptions.forEach(subscription ->
                        userSubscriptionService.findUsersBySubscription(subscription)
                                .ifPresent(users -> users.forEach(user ->
                                        emailUtil.sendSubscriptionNotify(user, subscription)
                                ))
                ));
    }

    private Optional<List<Subscription>> findAllByTransport(Transport transport) {
        return Optional.empty();//TODO
    }
}
