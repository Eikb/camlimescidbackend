package com.farukgenc.boilerplate.springboot.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STUDENTS")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Date createdAt;
    @NotEmpty(message = "Der Name des Schülers darf nicht fehlen")
    private String name;



}
