package com.academy.microservice_academy_students.application.Ports.Out;


import java.util.List;
import java.util.Optional;

import com.academy.microservice_academy_students.Domain.Entities.Student;

public interface IStudentPersistencePort {
    Student save(Student student);
    Optional<Student> findById(Long id);
    List<Student> findAllActive();
    Optional<Student> findByEmail(String email);

}
