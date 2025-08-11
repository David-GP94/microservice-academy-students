package com.academy.microservice_academy_students.Infrastructure.Adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.academy.microservice_academy_students.Domain.Entities.Student;
import com.academy.microservice_academy_students.Infrastructure.Repositories.StudentRepository;
import com.academy.microservice_academy_students.application.Ports.Out.IStudentPersistencePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaStudentAdapter implements IStudentPersistencePort {
    private final StudentRepository studentRepository;

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findByIdAndActiveTrueAndDeletedFalse(id);
    }

    @Override
    public List<Student> findAllActive() {
       return studentRepository.findByActiveTrueAndDeletedFalse();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmailAndActiveTrueAndDeletedFalse(email);
    }

}
