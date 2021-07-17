package com.fujfu.frontend.service;

import com.fujfu.frontend.listener.mo.EventMO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
@Slf4j
public class EventService {
    private final RabbitTemplate rabbitTemplate;

    public void send(String routingKey, String data) {
        send(UUID.randomUUID().toString(), routingKey, data);
    }

    public void send(String eventId, String routingKey, String data) {
        EventMO eventMO = new EventMO();
        eventMO.setEventId(eventId);
        eventMO.setData(data);
        rabbitTemplate.convertAndSend(routingKey, eventMO);
    }
}
