package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.exceptions.EntryException;
import com.farukgenc.boilerplate.springboot.exceptions.StudentException;
import com.farukgenc.boilerplate.springboot.model.Entry;
import com.farukgenc.boilerplate.springboot.model.Student;
import com.farukgenc.boilerplate.springboot.security.dto.EntryDto;
import com.farukgenc.boilerplate.springboot.security.dto.EntryRequest;
import com.farukgenc.boilerplate.springboot.security.dto.EntryResponse;
import com.farukgenc.boilerplate.springboot.security.dto.StudentResponse;
import com.farukgenc.boilerplate.springboot.service.EntryService;
import com.farukgenc.boilerplate.springboot.service.QrCodeService;
import com.farukgenc.boilerplate.springboot.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Tag(name = "Entry API")
@RequestMapping("/entry")
public class EntryController {

    private final EntryService entryService;

    private final StudentService studentService;
    private final QrCodeService qrCodeService;

    @PostMapping
    ResponseEntity<EntryResponse> createEntry(@Valid @RequestBody EntryRequest entryRequest){
        try {
            String studentId = qrCodeService.decryptCode(entryRequest.getEntcryptedQrCode());

            Student student = studentService.getStudentById(Integer.valueOf(studentId));
           Entry entry = new Entry();
           entry.setName(student.getName());
           entry.setInKurs(false);
           entry.setExit(LocalDateTime.now());
           entryService.createEntry(entry);

            return ResponseEntity.status(HttpStatus.CREATED).body(new EntryResponse(201,"Ausgang wurde verzeichnet ID: " + studentId));
        }catch (EntryException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EntryResponse(500,"Es kann kein zweiter Ausgang verzeichnet werden. Scannen Sie zuerst einen Eingang ein"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EntryResponse(500,"Code konnte nicht entschlüsselt werden"));
        }
    }

    @PostMapping("/enter")
    ResponseEntity<EntryResponse> addEnter(@Valid @RequestBody EntryRequest entryRequest){
        try {
            entryService.addEnter(entryRequest.getEntcryptedQrCode());

            return ResponseEntity.status(HttpStatus.CREATED).body(new EntryResponse(200,"Eingang wurde verzeichnet"));
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{date}")
    ResponseEntity<List<EntryDto>> getAllEntries(@PathVariable String date){
       return ResponseEntity.ok(entryService.getAllEntriesByDate(date));
    }

}
