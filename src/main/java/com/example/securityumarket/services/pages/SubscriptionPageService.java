package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.notification.NotificationRequest;
import com.example.securityumarket.dto.pages.subscription.SubscriptionRequest;
import com.example.securityumarket.dto.pages.subscription.SubscriptionResponse;
import com.example.securityumarket.dto.pages.subscription.SubscriptionTransportsResponse;
import com.example.securityumarket.dto.pages.catalog.request.RequestSearch;
import com.example.securityumarket.models.*;
import com.example.securityumarket.services.jpa.*;
import com.example.securityumarket.services.rabbitmq.producer.NotificationProducer;
import com.example.securityumarket.util.converter.transposrt_type.TransportConverter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    private final NotificationProducer notificationProducer;


    @Transactional
    public void addSubscription(RequestSearch requestSearch,
                                SubscriptionRequest subscriptionRequest) {
        Subscription subscription = subscriptionService
                .findByParameters(requestSearch).orElseGet(() ->
                        buildSubscriptionByRequestSearchDTO(requestSearch));
        subscriptionService.save(subscription);

        saveTransportSubscription(subscription);
        saveUserSubscription(subscription, subscriptionRequest);
    }

    @Transactional
    public void removeSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionService.findById(subscriptionId);

        Users authenticatedUser = userService.getAuthenticatedUser();
        userSubscriptionService.deleteBySubscriptionAndUser(subscription, authenticatedUser);

        deleteIfNotExistsBySubscription(subscription);
    }

    @Transactional
    public SubscriptionTransportsResponse getSubscription(long subscriptionId) {
        Subscription subscription = subscriptionService.findById(subscriptionId);

        Users authenticatedUser = userService.getAuthenticatedUser();
        UserSubscription userSubscription = userSubscriptionService
                .findBySubscriptionAndUser(subscription, authenticatedUser);

        SubscriptionTransportsResponse response = buildSubscriptionTransportsResponse(
                subscription, userSubscription.getLastUpdate());

        userSubscription.setLastUpdate(LocalDateTime.now());
        userSubscriptionService.save(userSubscription);
        return response;
    }

    @Transactional
    public void updateSubscription(Long subscriptionId, SubscriptionRequest subscriptionRequest) {
        Subscription subscription = subscriptionService.findById(subscriptionId);
        Users authenticatedUser = userService.getAuthenticatedUser();

        UserSubscription userSubscription = userSubscriptionService
                .findBySubscriptionAndUser(subscription, authenticatedUser);

        if (subscriptionRequest.notificationEnabled() != null) {
            userSubscription.setNotificationEnabled(subscriptionRequest.notificationEnabled());
        }
        if (subscriptionRequest.name() != null) {
            userSubscription.setName(subscriptionRequest.name());
        }
        userSubscriptionService.save(userSubscription);
    }

    @Transactional
    public void updateSubscriptionParameters(Long subscriptionId,
                                             RequestSearch requestSearch) {
        Subscription subscription = subscriptionService.findById(subscriptionId);
        Users authenticatedUser = userService.getAuthenticatedUser();

        UserSubscription userSubscription = userSubscriptionService
                .findBySubscriptionAndUser(subscription, authenticatedUser);

        if (requestSearch != null && !subscription.getParameters().equals(requestSearch)) {
            Subscription newSubscription = subscriptionService
                    .findByParameters(requestSearch).orElseGet(() ->
                            buildSubscriptionByRequestSearchDTO(requestSearch));
            subscriptionService.save(newSubscription);

            userSubscription.setSubscription(newSubscription);
            userSubscriptionService.save(userSubscription);
            saveTransportSubscription(newSubscription);

            deleteIfNotExistsBySubscription(subscription);
        }
    }

    public void notifyUsers(Transport transport) {
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
        userSubscriptionService.findAllBySubscription(subscription)
                .ifPresent(userSubscriptions -> userSubscriptions.stream()
                        .filter(UserSubscription::getNotificationEnabled)
                        .forEach(userSubscription -> notifyUser(userSubscription, transport)));
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
                .requestSearch(userSubscription.getSubscription()
                        .getParameters())
                .notificationStatus(userSubscription.getNotificationEnabled())
                .countNewTransports(
                        findUnseenTransports(
                                userSubscription.getSubscription(),
                                userSubscription.getLastUpdate()
                        ).size())
                .build();
    }

    private List<Transport> getTransportListByParameters(RequestSearch requestSearch) {
        Specification<Transport> specificationParam = catalogPageService
                .getSpecificationParam(requestSearch);
        return transportService.findAll(specificationParam);
    }

    private Subscription buildSubscriptionByRequestSearchDTO(RequestSearch requestSearch) {
        return Subscription.builder()
                .parameters(requestSearch)
                .build();
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

    private void deleteIfNotExistsBySubscription(Subscription subscription) {
        if (!userSubscriptionService.existsBySubscription(subscription)) {
            List<TransportSubscription> transportSubscriptions = transportSubscriptionService
                    .findAllBySubscription(subscription);

            transportSubscriptionService.deleteAll(transportSubscriptions);
            subscriptionService.delete(subscription);
        }
    }

    private void notifyUser(UserSubscription userSubscription, Transport transport) {
        notificationProducer.produce(buildNotificationRequest(
                userSubscription.getUser().getId(),
                userSubscription.getUser().getEmail(),
                userSubscription.getSubscription().getId(),
                userSubscription.getName(),
                transport));
    }

    private NotificationRequest buildNotificationRequest(
            Long userId, String email, Long subscriptionId, String subscriptionName, Transport transport) {
        return NotificationRequest.builder()
                .userId(userId)
                .email(email)
                .subscriptionId(subscriptionId)
                .subscriptionName(subscriptionName)
                .transportId(transport.getId())
                .transportDetail(buildStringTransportDetails(transport))
                .build();
    }

    private String buildStringTransportDetails(Transport transport) {
        return String.format("%s %s, %s - %s",
                transport.getTransportModel().getTransportTypeBrand().getTransportBrand().getBrand(),
                transport.getTransportModel().getModel(),
                transport.getCity().getDescription(),
                transport.getPrice());
    }

    public void removeTransportFromSubscription(Transport transport) {
        List<TransportSubscription> allByTransport = transportSubscriptionService
                .findAllByTransport(transport);
        transportSubscriptionService.deleteAll(allByTransport);
    }
}
