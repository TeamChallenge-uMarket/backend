package com.example.securityumarket.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String routingInternalNotificationKey;

    @Value("${rabbitmq.queues.filter-parameters}")
    private String filterParametersQueue;

    @Value("${rabbitmq.routing-keys.internal-filter-parameters}")
    private String routingInternalFilterParametersKey;

    @Bean
    public Queue filterParametersQueue() {
        return new Queue(filterParametersQueue);
    }

    @Bean
    public Binding filterParametersBinding() {
        return BindingBuilder
                .bind(filterParametersQueue())
                .to(internalExchange())
                .with(routingInternalFilterParametersKey);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalExchange())
                .with(routingInternalNotificationKey);
    }

    @Bean
    public TopicExchange internalExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

}