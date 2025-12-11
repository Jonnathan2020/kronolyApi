package com.kronoly.Entity;

import com.kronoly.Entity.Enuns.StatusAgendaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "TBL_AGENDA")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDA")
    private int idAgenda;

    @ElementCollection
    @CollectionTable(name = "AGENDA_HORARIOS", joinColumns = @JoinColumn(name = "ID_AGENDA"))
    @Column(name = "HORARIOS_DISPONIVEIS")
    public List<LocalDateTime> horarios = new ArrayList<>();

    @Column(name = "HORA_ALMOCO")
    public LocalDateTime horaAlmoco;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_AGENDA")
    public StatusAgendaEnum statusAgendaEnum;

    @Column(name = "HORA_ABERTURA")
    public LocalDateTime horaAbertura;

    @Column(name = "HORA_FECHAMENTO")
    public LocalDateTime horaFechamento;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;



}
