package com.academy.microservice_academy_students.Presentation.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.academy.microservice_academy_students.Domain.Entities.Student;
import com.academy.microservice_academy_students.Presentation.Dtos.StudentCreateRequestDto;
import com.academy.microservice_academy_students.Presentation.Dtos.StudentResponseDto;
import com.academy.microservice_academy_students.Presentation.Dtos.StudentUpdateRequestDto;

public interface StudentMapper {
    StudentResponseDto toDto(Student student);
    Student createEntityFromDto(StudentCreateRequestDto dto);
    void updateEntityFromDto(StudentUpdateRequestDto dto, Student student);
    List<StudentResponseDto> listStudentsToListDto(List<Student> students);
} 
