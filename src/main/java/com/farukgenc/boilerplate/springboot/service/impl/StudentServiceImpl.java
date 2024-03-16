package com.farukgenc.boilerplate.springboot.service.impl;

import com.farukgenc.boilerplate.springboot.exceptions.StudentException;
import com.farukgenc.boilerplate.springboot.model.Student;
import com.farukgenc.boilerplate.springboot.repository.StudentRepository;
import com.farukgenc.boilerplate.springboot.security.dto.StudentDto;
import com.farukgenc.boilerplate.springboot.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    @Override
    public List<StudentDto> getAllStudents() {
        List<StudentDto> studentDtoList = studentRepository.findAll()
                .stream()
                .map(student -> new StudentDto(student.getId(), student.getName()))
                .collect(Collectors.toList());
       return studentDtoList;
    }

    @Override
    public Student getStudentById(Integer studentId) {
        try{
            return studentRepository.findById(studentId).get();
        }catch (NoSuchElementException noSuchElementException){
            throw new StudentException("Der Schüler mit der ID: " + studentId + " konnte nicht gefunden werden");
        }
    }

    @Override
    public String createStudent(Student student) {
        try{
            studentRepository.save(student);
            return "Schüler wurde erstellt";
        }catch (StudentException studentException){
            throw new StudentException("Der Schüler konnte nicht angelegt werden. Fehlen Informationen?");
        }
    }

    @Override
    public void editStudent(Integer studentId, String newName) {
        try{
            studentRepository.updateNameById(newName, studentId);
        } catch (StudentException studentException){
            throw new StudentException("Schüler konnte nicht bearbeitet werden. Eingegebener Name: " + newName);
        }
    }

    @Override
    public void deleteStudent(Integer studentId) {
        try{
            studentRepository.deleteById(studentId);
        }catch (StudentException exception){
            throw new StudentException("Schüler konnte nicht gelöscht werden");
        }
    }
}
