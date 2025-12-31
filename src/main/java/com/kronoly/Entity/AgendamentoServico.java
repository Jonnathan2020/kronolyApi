package com.kronoly.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AGENDAMENTO_SERVICO")
public class AgendamentoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_AGENDAMENTO")
    private Agendamento agendamento;

    @ManyToOne
    @JoinColumn(name = "ID_SERVICO")
    private Servico servico;

    @Column(name = "TEMPO_ESTIMADO")
    private Long tempoEstimado;

    @Column(name = "valorCusto")
    private double valorCusto;

    @Column(name = "valorServico")
    private double valorServico;

    @Column(name = "QUANTIDADE_SERVICOS")
    private int quantServicos;
}
