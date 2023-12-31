package com.example.securityumarket.services.rabbitmq.consumer;

import com.example.securityumarket.dto.subscription.transport.adding.SubscriptionAddTransportRequest;
import com.example.securityumarket.dto.subscription.transport.removing.SubscriptionRemoveTransportRequest;
import com.example.securityumarket.models.Subscription;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.services.jpa.SubscriptionService;
import com.example.securityumarket.services.jpa.TransportService;
import com.example.securityumarket.services.jpa.TransportSubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionTransportConsumer {

    private final TransportService transportService;

    private final SubscriptionService subscriptionService;

    private final TransportSubscriptionService transportSubscriptionService;

    @RabbitListener(queues = {"${rabbitmq.queues.subscription.transport.adding}"})
    public void consume(SubscriptionAddTransportRequest request) {
        Transport transport = transportService.findTransportById(request.transportId());
        Subscription subscription = subscriptionService.findById(request.subscriptionId());

        transportSubscriptionService.save(transport, subscription);
        log.info(String.format("Transport ID %s saved -> for subscription ID %s.",
                request.transportId(), request.subscriptionId()));
    }

    @RabbitListener(queues = {"${rabbitmq.queues.subscription.transport.removing}"})
    public void consume(SubscriptionRemoveTransportRequest request) {
        Transport transport = transportService.findTransportById(request.transportId());
        transportSubscriptionService.deleteT(transport);
        log.info(String.format("Transport ID %s removed.",
                request.transportId()));
    }
}
