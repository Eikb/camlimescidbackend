package com.farukgenc.boilerplate.springboot.exceptions;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class EntryException extends RuntimeException{
    private String errorMessage;
}
