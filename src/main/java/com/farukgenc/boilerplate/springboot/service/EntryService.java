package com.farukgenc.boilerplate.springboot.service;


import com.farukgenc.boilerplate.springboot.model.Entry;
import com.farukgenc.boilerplate.springboot.security.dto.EntryDto;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

public interface EntryService {
    String createEntry(Entry entry) throws Exception;
    String addEnter(String entcryptedCode) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException;
    List<EntryDto> getAllEntriesByDate(String dateInString);
}
