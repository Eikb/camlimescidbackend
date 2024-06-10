package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.Student;
import com.farukgenc.boilerplate.springboot.security.dto.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> getAllStudents();
    Student getStudentById(Integer studentId);
    void createStudent(Student student);
    void createCollectionOfStudent(List<Student> students);
    void editStudent(Integer studentId, String newName);
    void deleteStudent(Integer studentId);


}
