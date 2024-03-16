package com.farukgenc.boilerplate.springboot.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EntryRequest {
    @NotEmpty(message = "Der QR-Code darf nicht leer sein")
    private String entcryptedQrCode;

}
