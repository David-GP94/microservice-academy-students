package com.academy.microservice_academy_students.Presentation.Controllers;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academy.microservice_academy_students.Domain.Entities.Student;
import com.academy.microservice_academy_students.Domain.Response.GlobalResponseHandler;
import com.academy.microservice_academy_students.Domain.Utils.CommonMethods;
import com.academy.microservice_academy_students.Presentation.Dtos.StudentCreateRequestDto;
import com.academy.microservice_academy_students.Presentation.Dtos.StudentResponseDto;
import com.academy.microservice_academy_students.Presentation.Dtos.StudentUpdateRequestDto;
import com.academy.microservice_academy_students.Presentation.Mappers.StudentMapper;
import com.academy.microservice_academy_students.application.Ports.In.IStudentServicePort;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final IStudentServicePort studentServicePort;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentMapper studentMapper;

    @PostMapping
    public ResponseEntity<GlobalResponseHandler<StudentResponseDto>> save(@Valid StudentCreateRequestDto request)
    {
        logger.info("Solictud recibida para guardar al estudiante:{}", request.getEmail());
        Student student = studentServicePort.save(studentMapper.createEntityFromDto(request));
        StudentResponseDto studentResponse = studentMapper.toDto(student);
        GlobalResponseHandler<StudentResponseDto> response = new GlobalResponseHandler<StudentResponseDto>(true, CommonMethods.GeneralMessages(6), studentResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponseHandler<StudentResponseDto>> findById(@PathVariable Long id)
    {
        logger.info("Solicitud recibida para devolver el estudiante: {}", id);
        Student student = studentServicePort.findById(id);
        StudentResponseDto studentResponse = studentMapper.toDto(student);
        GlobalResponseHandler<StudentResponseDto> response = new GlobalResponseHandler<StudentResponseDto>(true, CommonMethods.GeneralMessages(7), studentResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GlobalResponseHandler<List<StudentResponseDto>>> findAllActive()
    {
        logger.info("Solicitud recibida para devolver todos los estudiantes activos ");

        List<Student> students = studentServicePort.findAllActive();
        List<StudentResponseDto> studenstResponse = studentMapper.listStudentsToListDto(students);
        GlobalResponseHandler<List<StudentResponseDto>> response = new GlobalResponseHandler<List<StudentResponseDto>>(true, CommonMethods.GeneralMessages(8), studenstResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalResponseHandler<StudentResponseDto>> update(@PathVariable Long id, @RequestBody @Valid StudentUpdateRequestDto request)
    {
        logger.info("Solicitud recibida para actualizar el estudiante: {}", id);

        // Primero, obtener el estudiante existente
        Student existingStudent = studentServicePort.findById(id);
        // Actualizar los campos del estudiante existente con los datos del DTO
        studentMapper.updateEntityFromDto(request, existingStudent);
        // Guardar los cambios
        Student updatedStudent = studentServicePort.update(id, existingStudent);
        StudentResponseDto studentResponse = studentMapper.toDto(updatedStudent);
        GlobalResponseHandler<StudentResponseDto> response = new GlobalResponseHandler<StudentResponseDto>(true, CommonMethods.GeneralMessages(9), studentResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponseHandler<String>> delete(@PathVariable Long id)
    {
        logger.info("Solicitud recibida para eliminar el estudiante: {}", id);
        studentServicePort.delete(id);
        GlobalResponseHandler<String> response = new GlobalResponseHandler<String>(true, CommonMethods.GeneralMessages(10), null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
