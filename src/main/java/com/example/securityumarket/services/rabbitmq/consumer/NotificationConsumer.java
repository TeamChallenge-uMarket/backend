package com.example.securityumarket.services.rabbitmq.consumer;

import com.example.securityumarket.dto.notification.NotificationRequest;
import com.example.securityumarket.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationConsumer {

    private final EmailUtil emailUtil;

    @RabbitListener(queues = {"${rabbitmq.queues.notification}"})
    public void consume(NotificationRequest notification) {
        emailUtil.sendSubscriptionNotification(notification);
        log.info("Email notification sent -> for subscription ID {}, transport ID {}, to the user's ID {}.",
                notification.subscriptionId(), notification.transportId(), notification.userId());
    }
}
