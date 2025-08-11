package com.academy.microservice_academy_students.Presentation.Dtos;

import lombok.Data;

@Data
public class StudentResponseDto {
    private Long id;
    private String name;
    private String email;

}
