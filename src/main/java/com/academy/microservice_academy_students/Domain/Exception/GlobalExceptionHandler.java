package com.academy.microservice_academy_students.Domain.Exception;

import java.util.stream.Collectors;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.academy.microservice_academy_students.Domain.Exception.Custom.ExistingResourceException;
import com.academy.microservice_academy_students.Domain.Exception.Custom.ServiceNotAvailable;
import com.academy.microservice_academy_students.Domain.Response.GlobalResponseHandler;
import com.academy.microservice_academy_students.Domain.Utils.CommonMethods;

import lombok.RequiredArgsConstructor;


@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponseHandler<String>>  exceptionHandler(Exception ex){
        GlobalResponseHandler<String> response = new GlobalResponseHandler<String>(false, ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }  

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponseHandler<String>> ValidationExceptionHandle(MethodArgumentNotValidException ex)
    {
        String errors = ex.getBindingResult().getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining("; "));
        GlobalResponseHandler<String> response = new GlobalResponseHandler<String>(false, CommonMethods.GeneralMessages(4), errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /* Custom */
    @ExceptionHandler(ServiceNotAvailable.class)
    public ResponseEntity<GlobalResponseHandler<String>> serviceNotAvailable(ServiceNotAvailable ex)
    {
        GlobalResponseHandler<String> response = new GlobalResponseHandler<String>(false, CommonMethods.GeneralMessages(5), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GlobalResponseHandler<String>> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        GlobalResponseHandler<String> response = new GlobalResponseHandler<String>(false, CommonMethods.GeneralMessages(2), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistingResourceException.class)
    public ResponseEntity<GlobalResponseHandler<String>> existingResourceExceptionHandler(ExistingResourceException ex)
    {
        GlobalResponseHandler<String> response = new GlobalResponseHandler<String>(false,CommonMethods.GeneralMessages(3), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


}
