package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.exceptions.EntryException;
import com.farukgenc.boilerplate.springboot.exceptions.StudentException;
import com.farukgenc.boilerplate.springboot.model.Entry;
import com.farukgenc.boilerplate.springboot.model.Student;
import com.farukgenc.boilerplate.springboot.security.dto.EntryRequest;
import com.farukgenc.boilerplate.springboot.security.dto.EntryResponse;
import com.farukgenc.boilerplate.springboot.security.dto.StudentResponse;
import com.farukgenc.boilerplate.springboot.service.EntryService;
import com.farukgenc.boilerplate.springboot.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Tag(name = "Entry API")
@RequestMapping("/entry")
public class EntryController {

    private final EntryService entryService;

    private final StudentService studentService;

    @PostMapping
    ResponseEntity<EntryResponse> createEntry(@Valid @RequestBody EntryRequest entryRequest){
        try {
            String id = entryService.createEntry(entryRequest.getEntcryptedQrCode());
            if(studentService.getStudentById(Integer.valueOf(id)) == null){
                throw new StudentException("Student konnte nicht gefunden werden");
            }
           Student student = studentService.getStudentById(Integer.valueOf(id));
           Entry entry = new Entry();
           entry.setName(student.getName());
           entry.setInKurs(false);
           entry.setExit(new Date());
            return ResponseEntity.status(HttpStatus.CREATED).body(new EntryResponse(201,"Ausgang wurde verzeichnet ID: " + id));
        }catch (EntryException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EntryResponse(500,"Es kann kein zweiter Ausgang verzeichnet werden. Scannen Sie zuerst einen Eingang ein"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EntryResponse(500,"Code konnte nicht entschlüsselt werden"));

        }
    }
}
