package com.kronoly.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ServicoResumoDTO {

    private int idServico;
    private String descricao;
    private LocalDateTime tempoEstimado;
    private double valorCusto;
    private double valorServico;

    public ServicoResumoDTO(int idServico, String descricao, LocalDateTime tempoEstimado, double valorCusto, double valorServico) {
        this.idServico = idServico;
        this.descricao = descricao;
        this.tempoEstimado = tempoEstimado;
        this.valorCusto = valorCusto;
        this.valorServico = valorServico;
    }

    // Construtor auxiliar para conversão direta
    public ServicoResumoDTO(com.kronoly.Entity.Servico servico) {
        this.idServico = servico.getIdServico();
        this.descricao = servico.getDescricao();
        this.tempoEstimado = servico.getTempoEstimado();
        this.valorCusto = servico.getValorCusto();
        this.valorServico = servico.getValorServico();
    }
}
