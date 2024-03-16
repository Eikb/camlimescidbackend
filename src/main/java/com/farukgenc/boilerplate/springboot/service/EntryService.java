package com.farukgenc.boilerplate.springboot.service;


import com.farukgenc.boilerplate.springboot.model.Entry;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EntryService {
    String createEntry(String entcryptedCode) throws Exception;
    String addEnter(String entcryptedCode);
    List<Entry> getAllEntriesByDate(String dateInString);
}
