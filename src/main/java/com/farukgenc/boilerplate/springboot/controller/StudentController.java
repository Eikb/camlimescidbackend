package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.exceptions.StudentException;
import com.farukgenc.boilerplate.springboot.model.Student;
import com.farukgenc.boilerplate.springboot.security.dto.StudentDto;
import com.farukgenc.boilerplate.springboot.security.dto.StudentRequest;
import com.farukgenc.boilerplate.springboot.security.dto.StudentResponse;
import com.farukgenc.boilerplate.springboot.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Tag(name = "Students API")
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Operation(summary = "Gibt alle Talebes zurück", description = "gibt eine Liste der Talebes zurück")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Liste mit den aktuellen Talebes wird zurückgegeben. Wenn die Liste Leer ist existieren keine Talebes"), @ApiResponse(responseCode = "500", description = "Beim zurückgeben der Liste ist ein Fehler aufgetreten"), @ApiResponse(responseCode = "401", description = "Keine Berechtigung")

    })
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @Operation(summary = "Erstellt Schüler", description = "Erstellt einen Schüler mit einem Namen")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Schüler wurde erfolgreich erstellt"), @ApiResponse(responseCode = "500", description = "Beim Erstellen ist ein Fehler aufgetreten. Möglicherweise wurde kein Name eingegeben"), @ApiResponse(responseCode = "401", description = "Keine Berechtigung")

    })
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest studentRequest) {
        Student student = new Student();
        student.setName(studentRequest.getName());
        student.setCreatedAt(new Date());
        studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StudentResponse(201, "Student wurde erstellt"));
    }

    @PostMapping("/collection")
    public ResponseEntity<StudentResponse> createCollectionOfStudents(@Valid @RequestBody List<StudentRequest> studentRequests) {
        try {
            List<Student> students = studentRequests.stream().map(studentRequest -> {
                Student student = new Student();
                student.setName(studentRequest.getName());
                student.setCreatedAt(new Date());
                return student;
            }).collect(Collectors.toList());

            studentService.createCollectionOfStudent(students);
            return ResponseEntity.status(HttpStatus.CREATED).body(new StudentResponse(201, "Liste von Studenten wurde erstellt"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StudentResponse(500, "Beim erstellen der Studenten ist ein Fehler aufgetreten. " + e.getMessage()));

        }
    }

    @Operation(summary = "Ändert Namen vom Schüler", description = "Der Name von dem Studenten wird geändert")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Der Name des Schülers wurde geändert"), @ApiResponse(responseCode = "500", description = "Beim Updaten ist ein Fehler aufgetreten"), @ApiResponse(responseCode = "401", description = "Keine Berechtigung"), @ApiResponse(responseCode = "404", description = "Der Student wurde nicht gefunden")})
    @PutMapping("/{studentId}/{newName}")
    public ResponseEntity<StudentResponse> editStudent(@PathVariable String studentId, @PathVariable String newName) {
        try {
            if (studentService.getStudentById(Integer.valueOf(studentId)) != null) {
                studentService.editStudent(Integer.valueOf(studentId), newName);
                return ResponseEntity.status(HttpStatus.OK).body(new StudentResponse(200, "Der Name von dem Student mit der ID: " + studentId + " wurde geändert"));
            } else {
                throw new StudentException("Die ID : " + studentId + " wurde nicht gefunden");
            }
        } catch (StudentException studentException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StudentResponse(404, "Die ID : " + studentId + " wurde nicht gefunden"));
        }
    }

    @Operation(summary = "Löscht Schüler", description = "Mit Angabe der ID kann der Schüler gelöscht werden")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Der Schüler wurde erfolgreich gelöscht"), @ApiResponse(responseCode = "500", description = "Beim Löschen ist ein unbekannter Fehler aufgetreten"), @ApiResponse(responseCode = "401", description = "Keine Berechtigung"), @ApiResponse(responseCode = "404", description = "Der Student wurde nicht gefunden")})
    @DeleteMapping("/{studentId}")
    public ResponseEntity<StudentResponse> deleteStudent(@PathVariable String studentId) {
        try {
            if (studentService.getStudentById(Integer.valueOf(studentId)) != null) {
                studentService.deleteStudent(Integer.valueOf(studentId));
                return ResponseEntity.status(HttpStatus.OK).body(new StudentResponse(200, "Die ID: " + studentId + " wurde gelöscht"));
            } else {
                throw new StudentException("Die ID : " + studentId + " wurde nicht gefunden");
            }
        } catch (StudentException studentException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StudentResponse(404, "Die ID : " + studentId + " wurde nicht gefunden"));
        }
    }
}
