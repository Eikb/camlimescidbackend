package com.farukgenc.boilerplate.springboot.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentException extends RuntimeException{
    private final String errorMessage;
}
