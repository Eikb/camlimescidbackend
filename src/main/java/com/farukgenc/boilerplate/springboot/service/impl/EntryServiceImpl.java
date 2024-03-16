package com.farukgenc.boilerplate.springboot.service.impl;

import com.farukgenc.boilerplate.springboot.model.Entry;
import com.farukgenc.boilerplate.springboot.service.EntryService;
import com.farukgenc.boilerplate.springboot.service.QrCodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final QrCodeService qrCodeService;

    @Override
    public String createEntry(String entcryptedCode) throws Exception {
        try {
            String studentId = qrCodeService.decryptCode(entcryptedCode);

            return studentId.toString();
        }catch (Exception e){
            throw new Exception("Code konnte nicht entschlüsselt werden");
        }
    }

    @Override
    public String addEnter(String entcryptedCode) {
        return null;
    }

    @Override
    public List<Entry> getAllEntriesByDate(String dateInString) {
        return null;
    }
}
