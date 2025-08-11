package com.academy.microservice_academy_students.Presentation.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentCreateRequestDto {
    
    @NotBlank(message = "El nombre de estudiante es obligatorio")
    private String name;

    @NotBlank(message = "el email del estudiante es obligatorio")
    @Email(message = "El email debe ser valido")
    private String email;

}
