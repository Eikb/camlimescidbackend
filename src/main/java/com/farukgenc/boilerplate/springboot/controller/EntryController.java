package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.exceptions.EntryException;
import com.farukgenc.boilerplate.springboot.security.dto.EntryRequest;
import com.farukgenc.boilerplate.springboot.security.dto.EntryResponse;
import com.farukgenc.boilerplate.springboot.security.dto.StudentResponse;
import com.farukgenc.boilerplate.springboot.service.EntryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@Tag(name = "Entry API")
@RequestMapping("/entry")
public class EntryController {

    private final EntryService entryService;

    @PostMapping
    ResponseEntity<EntryResponse> createEntry(@Valid @RequestBody EntryRequest entryRequest){
        try {
            String id = entryService.createEntry(entryRequest.getEntcryptedQrCode());
            return ResponseEntity.status(HttpStatus.CREATED).body(new EntryResponse(201,"Ausgang wurde verzeichnet ID: " + id));
        }catch (EntryException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EntryResponse(500,"Es kann kein zweiter Ausgang verzeichnet werden. Scannen Sie zuerst einen Eingang ein"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EntryResponse(500,"Code konnte nicht entschlüsselt werden"));

        }
    }
}
