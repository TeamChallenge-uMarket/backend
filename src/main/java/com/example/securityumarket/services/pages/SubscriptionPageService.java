package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.pages.catalog.response.ResponseSearchDTO;
import com.example.securityumarket.dto.pages.subscription.SubscriptionRequest;
import com.example.securityumarket.dto.pages.subscription.SubscriptionResponse;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.dto.pages.catalog.request.RequestSearchDTO;
import com.example.securityumarket.models.*;
import com.example.securityumarket.services.jpa.*;
import com.example.securityumarket.services.notification.Observed;
import com.example.securityumarket.services.notification.Observer;
import com.example.securityumarket.util.EmailUtil;
import com.example.securityumarket.util.converter.transposrt_type.TransportConverter;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final TransportConverter transportConverter;
    private final EmailUtil emailUtil;


    @Transactional
    @Override
    public void addSubscription(RequestSearchDTO requestSearchDTO, SubscriptionRequest subscriptionRequest) {
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
            List<Transport> transports = getTransportListByParameters(subscription.getParameters());
            if (transports.contains(transport)) {
                transportSubscriptionService.save(transport, subscription);
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
            List<Users> list = userSubscriptions.stream()
                    .filter(UserSubscription::getNotificationEnabled)
                    .map(UserSubscription::getUser).toList();
            emailUtil.sendNotification(list, transport);
        }
    }


    private void saveUserSubscription(Subscription subscription, SubscriptionRequest subscriptionRequest) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        userSubscriptionService.save(authenticatedUser, subscription, subscriptionRequest);
    }

    private void saveTransportSubscription(Subscription subscription) {
        List<Transport> transports = getTransportListByParameters(subscription.getParameters());
        for (Transport transport : transports) {
            transportSubscriptionService.save(transport, subscription);
        }
    }

    private Subscription buildSubscriptionByRequestSearchDTO(RequestSearchDTO requestSearchDTO) {
        return Subscription.builder()
                .parameters(requestSearchDTO)
                .build();
    }

    public List<SubscriptionResponse> getSubscriptions() {
        Users authenticatedUser = userService.getAuthenticatedUser();
        List<UserSubscription> userSubscriptions = userSubscriptionService
                .findAllByUser(authenticatedUser);
        return userSubscriptions.stream().map(this::buildSubscriptionResponse).toList();
    }

    private int countNewTransports(UserSubscription userSubscriptions) {
        LocalDateTime lastUpdate = userSubscriptions.getLastUpdate();
        List<Transport> newTransports = findNewTransports(userSubscriptions.getSubscription(), lastUpdate);
        return newTransports.size();
    }

    private List<Transport> findNewTransports(Subscription subscription, LocalDateTime lastUpdate) {
        List<TransportSubscription> allBySubscription = transportSubscriptionService
                .findAllBySubscription(subscription);
        return allBySubscription.stream()
                .filter(sub -> sub.getLastUpdate().isAfter(lastUpdate))//TODO
                .map(TransportSubscription::getTransport)
                .toList();
    }

    private SubscriptionResponse buildSubscriptionResponse(UserSubscription subscription) {
        return SubscriptionResponse.builder()
                .id(subscription.getSubscription().getId())
                .name(subscription.getName())
                .requestSearchDTO(subscription.getSubscription().getParameters())
                .notificationStatus(subscription.getNotificationEnabled())
                .countNewTransports(countNewTransports(subscription))
                .build();
    }

    public List<ResponseSearchDTO> getSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionService.findById(subscriptionId)
                .orElseThrow(() -> new DataNotFoundException("Subscription by id"));

        updateLastUpdatedUserSubscription(subscription);

        List<Transport> transports = getTransportListByParameters(subscription.getParameters());
        return transportConverter.convertTransportListToTransportSearchDTO(transports);
    }

    private List<Transport> getTransportListByParameters(RequestSearchDTO requestSearchDTO){
        Specification<Transport> specificationParam = catalogPageService.getSpecificationParam(requestSearchDTO);
        return transportService.findAll(specificationParam);
    }

    private void updateLastUpdatedUserSubscription(Subscription subscription) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        UserSubscription userSubscription = userSubscriptionService
                .findBySubscriptionAndUser(subscription, authenticatedUser)
                .orElseThrow(() -> new DataNotFoundException("UserSubscription by Subscription And User"));

        userSubscription.setLastUpdate(LocalDateTime.now());
        userSubscriptionService.save(userSubscription);
    }
}
