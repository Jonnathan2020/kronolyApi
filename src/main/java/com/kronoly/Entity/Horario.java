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
@Table(name = "HORARIO_DISPONIVEIS")
public class Horario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO_DISPONIVEL")
    private int idHorario;

    @Column(name = "DATA_DISPONIVEL")
    private LocalDate data; // Ex: 2026-01-12

    @Column(name = "HORA_INICIO")
    private LocalTime horaInicio; // Ex: 08:00

    @Column(name = "HORA_FIM")
    private LocalTime horaFim; // Ex: 08:30 (Calculado com duracaoSlot)

    @Column(name = "disponivel")
    private boolean disponivel;

    @ManyToOne
    private Agenda agenda;

    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA")
    private Empresa empresa;
}
