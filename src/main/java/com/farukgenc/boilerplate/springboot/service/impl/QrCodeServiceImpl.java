package com.farukgenc.boilerplate.springboot.service.impl;

import com.farukgenc.boilerplate.springboot.service.QrCodeService;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
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
    private static final String SECRET_KEY = "1234567890123456"; // Beispiel-Schlüssel (16 Bytes)
    private static final String INIT_VECTOR = "1234567890123456"; // Beispiel-IV (16 Bytes)

    @Override
    public String decryptCode(String entcryptedCode) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(entcryptedCode);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
