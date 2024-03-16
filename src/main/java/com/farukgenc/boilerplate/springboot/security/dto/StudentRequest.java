package com.farukgenc.boilerplate.springboot.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class StudentRequest {
    @NotEmpty(message = "Der Name des Schülers darf nicht leer sein")
    private String name;
}
