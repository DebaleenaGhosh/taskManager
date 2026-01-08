package com.auth.AuthServer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitCommonConfig {

    public static final String USER_EVENTS_EXCHANGE = "user.events.exchange";
    // User Registration event
    public static final String USER_REGISTERED_ROUTING_KEY = "user.registered";
    public static final String USER_REGISTERED_QUEUE = "user.registered.queue";
    // User Deletion event
    public static final String USER_DELETED_QUEUE = "user.deleted.queue";

    @Bean
    public TopicExchange userEventsExchange()
    {
        return ExchangeBuilder.topicExchange(USER_EVENTS_EXCHANGE).durable(true).build();
    }
    /* User Registration */
    @Bean
    public Queue userRegisteredQueue()
    {
        return new Queue(USER_REGISTERED_QUEUE, true);
    }

    /* User Deletion */
    @Bean
    public Queue userDeletedQueue() {
        return new Queue(USER_DELETED_QUEUE, true);
    }

    // Auth service listens to user.deleted event
    @Bean
    public Binding bindingUserDeletedQueue(Queue userDeletedQueue, TopicExchange userEventsExchange)
    {
        return BindingBuilder.bind(userDeletedQueue).to(userEventsExchange).with("user.deleted");
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
