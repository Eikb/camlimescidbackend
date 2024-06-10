package com.farukgenc.boilerplate.springboot.service.impl;

import com.farukgenc.boilerplate.springboot.exceptions.StudentException;
import com.farukgenc.boilerplate.springboot.model.Student;
import com.farukgenc.boilerplate.springboot.repository.StudentRepository;
import com.farukgenc.boilerplate.springboot.security.dto.StudentDto;
import com.farukgenc.boilerplate.springboot.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    @Override
    public List<StudentDto> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(student -> new StudentDto(student.getId(), student.getName()))
                .sorted(Comparator.comparing(StudentDto::getId)) // Sortieren nach id
                .collect(Collectors.toList());
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
    public void createStudent(Student student) {
        try{
            studentRepository.save(student);
        }catch (StudentException studentException){
            throw new StudentException("Der Schüler konnte nicht angelegt werden. Fehlen Informationen?");
        }
    }

    @Override
    public void createCollectionOfStudent(List<Student> students) {
        try {
            studentRepository.saveAll(students);
        }catch (Exception e){
            throw new StudentException("Die Schüler konnten nicht angelegt werden " + e.getMessage());
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
