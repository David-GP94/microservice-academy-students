package com.academy.microservice_academy_students.Presentation.Dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class StudentUpdateRequestDto {
    
    private String name;

    @Email
    private String email;

}
