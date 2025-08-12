package com.academy.microservice_academy_students.application.Ports.Out;

public interface IEventPort {
    void publishEvent(String topic, String key, String message);

}
