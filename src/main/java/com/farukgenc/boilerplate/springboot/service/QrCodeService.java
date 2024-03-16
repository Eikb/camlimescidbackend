package com.farukgenc.boilerplate.springboot.service;

import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public interface QrCodeService {
    String decryptCode(String entcryptedCode) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException;
}
