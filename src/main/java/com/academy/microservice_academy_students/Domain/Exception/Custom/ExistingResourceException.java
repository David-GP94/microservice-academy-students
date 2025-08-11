package com.academy.microservice_academy_students.Domain.Exception.Custom;

public class ExistingResourceException extends RuntimeException {

    public ExistingResourceException(String message) {
        super(message);
    }

}
