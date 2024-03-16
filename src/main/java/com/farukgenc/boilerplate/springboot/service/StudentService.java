package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.Student;
import com.farukgenc.boilerplate.springboot.security.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {
    List<StudentDto> getAllStudents();
    Student getStudentById(Integer studentId);
    String createStudent(Student student);
    void editStudent(Integer studentId, String newName);
    void deleteStudent(Integer studentId);
}
