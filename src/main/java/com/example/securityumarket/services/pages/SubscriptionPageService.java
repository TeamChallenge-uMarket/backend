package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.pages.subscription.SubscriptionRequest;
import com.example.securityumarket.dto.pages.subscription.SubscriptionResponse;
import com.example.securityumarket.dto.pages.subscription.SubscriptionTransportsResponse;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.dto.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.*;
import com.example.securityumarket.services.jpa.*;
import com.example.securityumarket.util.EmailUtil;
import com.example.securityumarket.util.converter.transposrt_type.TransportConverter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@AllArgsConstructor
@Service
public class SubscriptionPageService {

    private final UserService userService;

    private final CatalogPageService catalogPageService;

    private final TransportService transportService;

    private final SubscriptionService subscriptionService;

    private final UserSubscriptionService userSubscriptionService;

    private final TransportSubscriptionService transportSubscriptionService;

    private final TransportConverter transportConverter;

    private final EmailUtil emailUtil;


    @Transactional
    public void addSubscription(RequestSearchDTO requestSearchDTO,
                                SubscriptionRequest subscriptionRequest) {
        Subscription subscription = subscriptionService
                .findByParameters(requestSearchDTO).orElseGet(() ->
                        buildSubscriptionByRequestSearchDTO(requestSearchDTO));

        if (subscriptionService.findByParameters(subscription.getParameters()).isEmpty()) {
            subscriptionService.save(subscription);
        }

        saveTransportSubscription(subscription);
        saveUserSubscription(subscription, subscriptionRequest);
    }

    @Transactional
    public void removeSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionService.findById(subscriptionId)
                .orElseThrow(() -> new DataNotFoundException("Subscription by id"));

        Users authenticatedUser = userService.getAuthenticatedUser();
        userSubscriptionService.deleteBySubscriptionAndUser(subscription, authenticatedUser);

        if (!userSubscriptionService.existsBySubscription(subscription)) {
            subscriptionService.delete(subscription);
        }
    }

    public void addTransport(Transport transport) {
        List<Subscription> all = subscriptionService.findAll();
        for (Subscription subscription : all) {
            List<Transport> transports = getTransportListByParameters(subscription
                    .getParameters());
            if (transports.contains(transport)) {
                transportSubscriptionService.save(transport, subscription);
                notifyObservers(subscription, transport);
            }
        }
    }

    public void notifyObservers(Subscription subscription, Transport transport) {
        Optional<List<UserSubscription>> allBySubscription = userSubscriptionService
                .findAllBySubscription(subscription);
        if (allBySubscription.isPresent()) {
            List<UserSubscription> userSubscriptions = allBySubscription.get();
            List<Users> list = userSubscriptions.stream()
                    .filter(UserSubscription::getNotificationEnabled)
                    .map(UserSubscription::getUser).toList();
            emailUtil.sendNotification(list, transport);
        }
    }


    private void saveUserSubscription(Subscription subscription,
                                      SubscriptionRequest subscriptionRequest) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        userSubscriptionService.save(
                authenticatedUser, subscription, subscriptionRequest);
    }

    private void saveTransportSubscription(Subscription subscription) {
        List<Transport> transports = getTransportListByParameters(subscription
                .getParameters());
        for (Transport transport : transports) {
            transportSubscriptionService.save(transport, subscription);
        }
    }

    @Transactional
    public SubscriptionTransportsResponse getSubscription(long subscriptionId) {
        Subscription subscription = subscriptionService.findById(subscriptionId)
                .orElseThrow(() -> new DataNotFoundException("Subscription by id"));

        Users authenticatedUser = userService.getAuthenticatedUser();
        UserSubscription userSubscription = userSubscriptionService
                .findBySubscriptionAndUser(subscription, authenticatedUser)
                .orElseThrow(() -> new DataNotFoundException("UserSubscription by Subscription And User"));

        SubscriptionTransportsResponse response = buildSubscriptionTransportsResponse(
                subscription, userSubscription.getLastUpdate());

        userSubscription.setLastUpdate(LocalDateTime.now());
        userSubscriptionService.save(userSubscription);
        return response;
    }

    private SubscriptionTransportsResponse buildSubscriptionTransportsResponse(
            Subscription subscription,
            LocalDateTime lastViewedSubscription) {

        List<Transport> unseenTransports = findUnseenTransports(subscription,
                lastViewedSubscription);
        List<Transport> viewedTransports = findViewedTransports(subscription,
                lastViewedSubscription);

        return SubscriptionTransportsResponse.builder()
                .unseenTransportList(transportConverter
                        .convertTransportListToResponseSearchDTO(unseenTransports))
                .viewedTransportList(transportConverter
                        .convertTransportListToResponseSearchDTO(viewedTransports))
                .build();
    }

    private List<Transport> findUnseenTransports(Subscription subscription,
                                                 LocalDateTime lastViewedSubscription) {
        List<TransportSubscription> allBySubscription = transportSubscriptionService
                .findAllBySubscription(subscription);

        return filterTransports(allBySubscription, sub ->
                sub.getLastUpdate().isAfter(lastViewedSubscription));
    }

    private List<Transport> findViewedTransports(Subscription subscription,
                                                 LocalDateTime lastViewedSubscription) {
        List<TransportSubscription> allBySubscription = transportSubscriptionService
                .findAllBySubscription(subscription);

        return filterTransports(allBySubscription, sub ->
                sub.getLastUpdate().isBefore(lastViewedSubscription));
    }

    private List<Transport> filterTransports(List<TransportSubscription> subscriptions,
                                             Predicate<TransportSubscription> filter) {
        return subscriptions.stream()
                .filter(filter)
                .map(TransportSubscription::getTransport)
                .toList();
    }


    public List<SubscriptionResponse> getSubscriptions() {
        Users authenticatedUser = userService.getAuthenticatedUser();
        List<UserSubscription> userSubscriptions = userSubscriptionService
                .findAllByUser(authenticatedUser);
        return userSubscriptions
                .stream()
                .map(this::buildSubscriptionResponse)
                .toList();
    }


    private SubscriptionResponse buildSubscriptionResponse(UserSubscription userSubscription) {
        return SubscriptionResponse.builder()
                .id(userSubscription.getSubscription().getId())
                .name(userSubscription.getName())
                .requestSearchDTO(userSubscription.getSubscription()
                        .getParameters())
                .notificationStatus(userSubscription.getNotificationEnabled())
                .countNewTransports(
                        findUnseenTransports(
                                userSubscription.getSubscription(),
                                userSubscription.getLastUpdate()
                        ).size())
                .build();
    }

    private List<Transport> getTransportListByParameters(RequestSearchDTO requestSearchDTO) {
        Specification<Transport> specificationParam = catalogPageService
                .getSpecificationParam(requestSearchDTO);
        return transportService.findAll(specificationParam);
    }

    private Subscription buildSubscriptionByRequestSearchDTO(RequestSearchDTO requestSearchDTO) {
        return Subscription.builder()
                .parameters(requestSearchDTO)
                .build();
    }
}
