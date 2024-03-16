package com.farukgenc.boilerplate.springboot.repository;

import com.farukgenc.boilerplate.springboot.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Transactional
    @Modifying
    @Query("update Student s set s.name = ?1 where s.id = ?2")
    int updateNameById(String name, Integer id);
}
