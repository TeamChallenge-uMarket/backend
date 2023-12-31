package com.example.securityumarket.services.rabbitmq.producer;

import com.example.securityumarket.dto.subscription.transport.adding.SubscriptionAddTransportRequest;
import com.example.securityumarket.dto.subscription.transport.removing.SubscriptionRemoveTransportRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionTransportProducer {

    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.exchanges.internal}")
    private String exchange;

    @Value("${rabbitmq.routing-keys.internal-subscription-transport.adding}")
    private String routingInternalTransportAddingKey;

    @Value("${rabbitmq.routing-keys.internal-subscription-transport.removing}")
    private String routingInternalTransportRemovingKey;

    public void produce(SubscriptionAddTransportRequest request) {
        rabbitTemplate.convertAndSend(exchange, routingInternalTransportAddingKey, request);
    }

    public void produce(SubscriptionRemoveTransportRequest request) {
        rabbitTemplate.convertAndSend(exchange, routingInternalTransportRemovingKey, request);
    }
}
