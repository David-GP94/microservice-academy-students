package com.academy.microservice_academy_students.Domain.Exception.Custom;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
