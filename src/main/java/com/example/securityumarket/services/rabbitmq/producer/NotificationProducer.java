package com.example.securityumarket.services.rabbitmq.producer;

import com.example.securityumarket.dto.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.exchanges.internal}")
    private String exchange;

    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String routingKey;

    public void produce(NotificationRequest notification) {
        rabbitTemplate.convertAndSend(exchange, routingKey, notification);
    }
}
