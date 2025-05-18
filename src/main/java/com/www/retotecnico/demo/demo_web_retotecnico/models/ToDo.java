package com.www.retotecnico.demo.demo_web_retotecnico.models;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private LocalDate fecha;
    @Column(nullable = false)
    private String estadus;

    public ToDo() {

    }
}
