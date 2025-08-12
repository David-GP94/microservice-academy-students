package com.academy.microservice_academy_students.Domain.Exception.Custom;

public class ServiceNotAvailable extends RuntimeException {

    public ServiceNotAvailable(String message) {
        super(message);
    }

}
