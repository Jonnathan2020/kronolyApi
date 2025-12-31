package com.kronoly.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "HORARIOS_DISPONIVEIS")
public class Horarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO_DISPONIVEL")
    private int idHorario;

    @Column(name = "DATA_HORA")
    public LocalDateTime dataHora;

    @Column(name = "disponivel")
    public boolean disponivel;

    @ManyToOne
    private Agenda agenda;

    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA")
    private Empresa empresa;
}
