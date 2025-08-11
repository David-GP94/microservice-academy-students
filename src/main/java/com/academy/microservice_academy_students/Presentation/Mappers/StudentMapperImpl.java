package com.academy.microservice_academy_students.Presentation.Mappers;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.academy.microservice_academy_students.Domain.Entities.Student;
import com.academy.microservice_academy_students.Presentation.Dtos.StudentCreateRequestDto;
import com.academy.microservice_academy_students.Presentation.Dtos.StudentResponseDto;
import com.academy.microservice_academy_students.Presentation.Dtos.StudentUpdateRequestDto;

@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentResponseDto toDto(Student student) {
        if (student == null) {
            return null;
        }
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        return dto;
    }

    @Override
    public Student createEntityFromDto(StudentCreateRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setActive(true);
        student.setDeleted(false);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdateAt(LocalDateTime.now());
        return student;
    }

    @Override
    public void updateEntityFromDto(StudentUpdateRequestDto dto, Student student) {
        if (dto == null || student == null) {
            return;
        }
        if (dto.getName() != null) {
            student.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            student.setEmail(dto.getEmail());
        }
        student.setUpdateAt(LocalDateTime.now());
    }

    @Override
    public List<StudentResponseDto> listStudentsToListDto(List<Student> students) {
        if (students == null) {
            return null;
        }
        return students.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
