package com.academy.microservice_academy_students.application.Services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.academy.microservice_academy_students.Domain.Entities.Student;
import com.academy.microservice_academy_students.Domain.Exception.Custom.ExistingResourceException;
import com.academy.microservice_academy_students.Domain.Exception.Custom.ResourceNotFoundException;
import com.academy.microservice_academy_students.Domain.Exception.Custom.ServiceNotAvailable;
import com.academy.microservice_academy_students.application.Ports.In.IStudentServicePort;
import com.academy.microservice_academy_students.application.Ports.Out.ICachePort;
import com.academy.microservice_academy_students.application.Ports.Out.IEventPort;
import com.academy.microservice_academy_students.application.Ports.Out.IStudentPersistencePort;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentServicePort {
    private final IStudentPersistencePort persistencePort;
    public static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final IEventPort eventPort;
    private final ICachePort cachePort;

    @Override
    @CircuitBreaker(name = "StudentService", fallbackMethod = "saveFallback")
    public Student save(Student student) {
        logger.info("Saving student with email: {}", student.getEmail());

        //Validar Estudiante existente
        Optional<Student> studentExists = persistencePort.findByEmail(student.getEmail());
        if (studentExists.isPresent()) {
            throw new ExistingResourceException("El estudiante con el correo: " + student.getEmail() + " ya existe");
        }

        Student studentSaved = persistencePort.save(student);
        cachePort.saveToCache(studentSaved.getId(), studentSaved);
        eventPort.publishEvent("student-created", studentSaved.getEmail(), "Estudiante creado: "+ studentSaved.getName());
        return studentSaved;

    }

    @Override
    @CircuitBreaker(name = "StudenService", fallbackMethod = "findByIdFallback")
    public Student findById(Long id) {
        logger.info("Obteniendo estudiante con el id:{}", id);
        Optional<Student> cachedStudent = cachePort.findByIdFromCache(id);
        if (cachedStudent.isPresent()) {
            logger.info("Estudiante econtrado en cache: {}", id);
            return cachedStudent.get();
        }

        Student studentFound = persistencePort.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Estudiante con el id: " + id + " no encontrado."));
        cachePort.saveToCache(id, studentFound);

        return studentFound;

    }

    @Override
    public List<Student> findAllActive() {
        logger.info("Obteniendo todos los estudiantes activos");
        return persistencePort.findAllActive();
    }

    @Override
    public Student update(Long id, Student student) {
        logger.info("Actualizando estudiante con el id:{} ", id);
        //Validar si el estudiante existe existe
        Student studentExists = findById(id);
        if (studentExists == null) {
            throw new ResourceNotFoundException("El estudiante con el id:" + id + "no fue encontrado");
        }

        //Validar que el email no se encuentre previamente registrado
        if (!student.getEmail().isEmpty()) {
            Optional<Student> studentEmailExists = persistencePort.findByEmail(student.getEmail());
            if (studentEmailExists.isPresent() && id != studentEmailExists.get().getId()) {
                throw new ExistingResourceException("ya existe un estudiante con este correo:" + student.getEmail());
            }
            studentExists.setEmail(student.getEmail());
        }
        studentExists.setName(student.getName());
        Student studentUpdated = persistencePort.save(studentExists);
        cachePort.saveToCache(id, studentUpdated);
        return studentUpdated;
        
    }

    @Override
    public void delete(Long id) {
        logger.info("Borrando estudiante con el id:" + id);
        Student existingStudent = findById(id);
        if (existingStudent == null) {
            throw new ResourceNotFoundException("El estudiante con el id: " + id + " no existe");
        }
        existingStudent.setActive(false);
        existingStudent.setDeleted(true);
        persistencePort.save(existingStudent);
        cachePort.deleteFromCache(id);
    }


    //FALLBACKS

    public void saveFallback(Throwable th)
    {
        logger.error("Fallback for save: {}", th.getMessage());
        throw new ServiceNotAvailable("El servicio para guardar estudiantes no se encuentra disponible" + th.getMessage());
    }

    public void findByIdFallback(Throwable th)
    {
        logger.error("Fallback for findById Studen: {}", th.getMessage());
        throw new ServiceNotAvailable("El servico para obtener Estudiantes por Id no se encuentra disponible" + th.getMessage());
    }

}
