package com.farukgenc.boilerplate.springboot.service.impl;

import com.farukgenc.boilerplate.springboot.service.QrCodeService;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;


@Service
public class QrCodeServiceImpl implements QrCodeService {
    private static final String secretKey = "CamliMescidKing";

    @Override
    public String decryptCode(String entcryptedCode) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "Rabbit");
        Cipher cipher = Cipher.getInstance("Rabbit");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(entcryptedCode));
        return new String(decryptedBytes);
    }
}
