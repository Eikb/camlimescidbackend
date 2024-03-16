package com.farukgenc.boilerplate.springboot.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntryResponse {
    private Integer statusCode;
    private String message;
}
