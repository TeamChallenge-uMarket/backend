package com.example.securityumarket.services.rabbitmq.consumer;

import com.example.securityumarket.dto.filters.request.UpdateFilterParametersRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class FilterParametersConsumer {

//    private final RedisService redisService;
//
//    @RabbitListener(queues = {"${rabbitmq.queues.filter-parameters}"})
//    public void consume(UpdateFilterParametersRequest request) {
//        redisService.updateFilterParameters(request);
//        log.info(String.format("Filter parameters updated. Filed %s -> new value = %s.",
//                request.field(), request.value()));
//    }

}
