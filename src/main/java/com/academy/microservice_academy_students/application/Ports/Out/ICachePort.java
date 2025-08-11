package com.academy.microservice_academy_students.application.Ports.Out;

import java.util.Optional;

import com.academy.microservice_academy_students.Domain.Entities.Student;

public interface ICachePort {
    void saveToCache(Long id, Student student);
    Optional<Student> findByIdFromCache(Long id);
    void deleteFromCache(Long id);

}
