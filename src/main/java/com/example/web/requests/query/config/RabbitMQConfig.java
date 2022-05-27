package com.example.web.requests.query.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * This Class provide basic {@link Configuration} for RabbitMQ service.
 */
@Configuration
public class RabbitMQConfig {

    public static String REQUEST_QUEUE;

    @Value("${spring.rabbitmq.request-queue.name}")
    public void setDirectory(String value) {
        REQUEST_QUEUE = value;
    }

    @Bean
    Queue requestsQueue() {
        return new Queue(REQUEST_QUEUE, false);
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(rabbitConnectionFactory());
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate() {
        return new AsyncRabbitTemplate(rabbitTemplate());
    }

    @Bean
    public Map<UUID, AsyncRabbitTemplate.RabbitMessageFuture> nameMap(){
        return new HashMap<>();
    }

}
