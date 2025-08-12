package com.academy.microservice_academy_students.application.Ports.In;

import java.util.List;


import com.academy.microservice_academy_students.Domain.Entities.Student;

public interface IStudentServicePort {
    Student save(Student student);
    Student findById(Long id);
    List<Student> findAllActive();
    Student update(Long id, Student student);
    void delete(Long id);
}
