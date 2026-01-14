package com.kronoly.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "TBL_HORARIOS_DISPONIVEIS",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"DATA_DISPONIVEL", "HORA_INICIO"})
        })
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO_DISPONIVEL")
    private int idHorario;

    @Column(name = "DATA_DISPONIVEL", columnDefinition = "DATE", nullable = false)
    private LocalDate data;

    @Column(name = "HORA_INICIO", columnDefinition = "TIME", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "HORA_FIM", columnDefinition = "TIME", nullable = false)
    private LocalTime horaFim;

    @Column(name = "DISPONIVEL", nullable = false)
    private boolean disponivel;

    @ManyToOne
    private Agenda agenda;

    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA")
    private Empresa empresa;
}

