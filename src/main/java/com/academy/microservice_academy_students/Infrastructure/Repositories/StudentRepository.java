package com.academy.microservice_academy_students.Infrastructure.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academy.microservice_academy_students.Domain.Entities.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIdAndActiveTrueAndDeletedFalse(Long id);
    List<Student> findByActiveTrueAndDeletedFalse();
    Optional<Student> findByEmailAndActiveTrueAndDeletedFalse(String email);

}
