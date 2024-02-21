package com.example.securityumarket.services.rabbitmq.consumer;

import com.example.securityumarket.dto.filters.request.UpdateFilterParametersRequest;
import com.example.securityumarket.services.redis.FilterParametersService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class FilterParametersConsumer {

    private final FilterParametersService filterParametersService;

    @RabbitListener(queues = {"${rabbitmq.queues.filter-parameters}"})
    public void consume(UpdateFilterParametersRequest request) {
        filterParametersService.saveFilterParameters(request.transportTypeId());
        log.info("Filter parameters for transport type ID {} updated.", request.transportTypeId());
    }

}
