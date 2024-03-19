package com.example.securityumarket.services.rabbitmq.producer;

import com.example.securityumarket.dto.filters.request.UpdateFilterParametersRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FilterParametersProducer {

    private final RabbitTemplate rabbitTemplate;


    @Value("${rabbitmq.exchanges.internal}")
    private String exchange;

    @Value("${rabbitmq.routing-keys.internal-filter-parameters}")
    private String routingKey;

    public void produce(UpdateFilterParametersRequest request) {
        rabbitTemplate.convertAndSend(exchange, routingKey, request);
    }

}
