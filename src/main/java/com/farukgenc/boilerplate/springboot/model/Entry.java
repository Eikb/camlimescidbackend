package com.farukgenc.boilerplate.springboot.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ENTRIES")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;
    Date exit;
    Date enter;
    String name;
    Boolean inKurs;
}
