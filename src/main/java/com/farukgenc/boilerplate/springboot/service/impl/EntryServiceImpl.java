package com.farukgenc.boilerplate.springboot.service.impl;

import com.farukgenc.boilerplate.springboot.exceptions.EntryException;
import com.farukgenc.boilerplate.springboot.model.Entry;
import com.farukgenc.boilerplate.springboot.model.EntryRepository;
import com.farukgenc.boilerplate.springboot.model.Student;
import com.farukgenc.boilerplate.springboot.security.dto.EntryDto;
import com.farukgenc.boilerplate.springboot.service.EntryService;
import com.farukgenc.boilerplate.springboot.service.QrCodeService;
import com.farukgenc.boilerplate.springboot.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final QrCodeService qrCodeService;
    private final EntryRepository entryRepository;
    private final StudentService studentService;

    @Override
    public String createEntry( Entry entry) throws Exception {
        try {

            if(entryRepository.findByNameAndEnterNull(entry.getName()) == null){
                entryRepository.save(entry);
                return "Entry erstellt";
            }else {
                throw new EntryException("Es gibt noch eine offene Entry");
            }
        }
        catch (EntryException e){
            throw new EntryException(e.getMessage());
        }
        catch (Exception e){
            throw new Exception("Code konnte nicht entschlüsselt werden");
        }
    }

    @Override
    public String addEnter(String entcryptedCode) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
       try {
           String studentId = qrCodeService.decryptCode(entcryptedCode);
           Student student = studentService.getStudentById(Integer.valueOf(studentId));
           Entry entry = entryRepository.findByEnterNullAndInKursFalseAndName(student.getName());
           entryRepository.updateInKursById(true, entry.getId());
           entryRepository.updateEnterById(LocalDateTime.now(),entry.getId());
           return "Eingang hinzugefügt";
       }catch (NullPointerException e){
           throw new EntryException(e.getMessage());
       }

    }

    @Override
    public List<EntryDto> getAllEntriesByDate(String dateInString) {
        String[] dateInStringArray = dateInString.split("-");
        LocalDate date = LocalDate.parse(dateInStringArray[2] + "-" + dateInStringArray[1] + "-" +dateInStringArray[0]);


    return entryRepository.findAll().stream().filter(actualDate -> {
        LocalDate first = actualDate.getExit().toLocalDate();
        return first.equals(date);
    }).map(entry -> {
        EntryDto entryDto = new EntryDto();
        entryDto.setName(entry.getName());
        entryDto.setExit(String.format("%02d:%02d:%02d", entry.getExit().getHour(), entry.getExit().getMinute(), entry.getExit().getSecond()));
        if(Objects.nonNull(entry.getEnter())){
            entryDto.setEnter(String.format("%02d:%02d:%02d", entry.getEnter().getHour(), entry.getEnter().getMinute(), entry.getEnter().getSecond()));
        }
        entryDto.setInKurs(false);
        return entryDto;
    }).collect(Collectors.toList());
    }
}
