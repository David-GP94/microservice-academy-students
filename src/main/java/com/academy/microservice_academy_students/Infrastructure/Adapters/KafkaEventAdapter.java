package com.academy.microservice_academy_students.Infrastructure.Adapters;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.academy.microservice_academy_students.application.Ports.Out.IEventPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaEventAdapter implements IEventPort {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publishEvent(String topic, String key, String message) {
        kafkaTemplate.send(topic, key,  message);
    }

}
