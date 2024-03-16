package com.farukgenc.boilerplate.springboot.exceptions;

import com.farukgenc.boilerplate.springboot.controller.RegistrationController;
import com.farukgenc.boilerplate.springboot.controller.StudentController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackageClasses = StudentController.class)
public class StudentControllerAdvice {
    @ExceptionHandler(StudentException.class)
    ResponseEntity<ApiExceptionResponse> handleStudentException(StudentException exception) {
        final ApiExceptionResponse response = new ApiExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return  ResponseEntity.status(response.getStatus()).body(response);
    }

}
