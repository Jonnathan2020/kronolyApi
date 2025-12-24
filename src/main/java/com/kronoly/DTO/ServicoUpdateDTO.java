package com.kronoly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoUpdateDTO {

    private int idServico;
    private String descricao;
    private Long tempoEstimado;
    private double valorCusto;
    private double valorServico;

    public ServicoUpdateDTO(int idServico, String descricao, Long tempoEstimado, double valorCusto, double valorServico) {
        this.idServico = idServico;
        this.descricao = descricao;
        this.tempoEstimado = tempoEstimado;
        this.valorCusto = valorCusto;
        this.valorServico = valorServico;
    }

    // Construtor auxiliar para conversão direta
    public ServicoUpdateDTO(com.kronoly.Entity.Servico servico) {
        this.idServico = servico.getIdServico();
        this.descricao = servico.getDescricao();
        this.tempoEstimado = servico.getTempoEstimado();
        this.valorCusto = servico.getValorCusto();
        this.valorServico = servico.getValorServico();
    }
}
