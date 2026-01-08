package com.auth.AuthServer.service;

import com.auth.AuthServer.config.RabbitCommonConfig;
import com.auth.AuthServer.dto.UserEventDto;
import com.auth.AuthServer.repository.AuthUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthEventListener
{
    @Autowired
    AuthUserRepository authUserRepository;

    @RabbitListener(queues = RabbitCommonConfig.USER_DELETED_QUEUE )
    public void handleUserDeleted(UserEventDto event)
    {
        log.info("User deleted event received: {} for user ID: {}", event.getEventType(), event.getUserId());
        authUserRepository.deleteById(event.getUserId());
    }
}
