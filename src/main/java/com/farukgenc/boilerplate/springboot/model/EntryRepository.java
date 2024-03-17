package com.farukgenc.boilerplate.springboot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Integer> {
    @Transactional
    @Modifying
    @Query("update Entry e set e.inKurs = ?1 where e.id = ?2")
    int updateInKursById(Boolean inKurs, Integer id);
    Entry findByNameAndEnterNull(String name);
    Entry findByEnterNotNullAndName(String name);
    Entry findByEnterNullAndInKursFalseAndName(String name);
    @Transactional
    @Modifying
    @Query("update Entry e set e.enter = ?1 where e.id = ?2")
    int updateEnterById(LocalDateTime enter, Integer id);
    Entry findByInKursFalseAndEnterNull();
    List<Entry> findByExit(Date exit);
}