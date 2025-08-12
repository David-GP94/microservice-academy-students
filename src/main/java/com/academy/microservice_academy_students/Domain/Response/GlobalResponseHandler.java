package com.academy.microservice_academy_students.Domain.Response;


import lombok.Data;

@Data
public class GlobalResponseHandler<T> {
    private Boolean succes;
    private String messsage;
    private T data;

    public GlobalResponseHandler(Boolean succes, String messsage, T data) {
        this.succes = succes;
        this.messsage = messsage;
        this.data = data;
    }

}
