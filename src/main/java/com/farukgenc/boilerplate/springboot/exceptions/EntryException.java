package com.farukgenc.boilerplate.springboot.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EntryException extends RuntimeException{
    private String errorMessage;
}
