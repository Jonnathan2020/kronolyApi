package com.kronoly.Entity;

import com.kronoly.Entity.Enuns.StatusAgendaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Horario> horarios = new ArrayList<>();

    @Column(name = "HORA_ALMOCO")
    public LocalTime horaAlmoco;

    @Column(name = "HORA_RETORNO_ALMOCO")
    private LocalTime horaRetornoAlmoco;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_AGENDA")
    public StatusAgendaEnum statusAgendaEnum;

    @Column(name = "HORA_ABERTURA")
    public LocalTime horaAbertura;

    @Column(name = "HORA_FECHAMENTO")
    public LocalTime horaFechamento;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @Column(name = "CRIADO_EM")
    private LocalDateTime criadoEm;

    @Column(name = "ATUALIZADO_EM")
    private LocalDateTime atualizadoEm;

    //permite definir o periodo de horarios livres
    @Column(name = "DURACAO_SLOT")
    private int duracaoSlot; // ex: 30 minutos

    @ElementCollection
    @CollectionTable(
            name = "AGENDA_DIAS_SEMANA",
            joinColumns = @JoinColumn(name = "ID_AGENDA")
    )
    @Column(name = "DIA_SEMANA")
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> diasSemana = new ArrayList<>();

}
