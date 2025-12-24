package com.kronoly.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "TBL_SERVICO")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SERVICO")
    private int idServico;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "TEMPO_ESTIMADO")
    private Long tempoEstimado;

    @Column(name = "VALOR_CUSTO")
    private double valorCusto;

    @Column(name = "VALOR_SERVICO")
    private double valorServico;

    @ManyToOne
    @JoinColumn(name = "ID_AGENDAMENTO")
    private Agendamento agendamento;

}
