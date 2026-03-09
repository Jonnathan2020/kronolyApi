package com.kronoly.DTO;

import com.kronoly.Entity.Servico;
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
    private boolean ativo = true;

    public ServicoUpdateDTO(int idServico, String descricao, Long tempoEstimado, double valorCusto, double valorServico, boolean ativo) {
        this.idServico = idServico;
        this.descricao = descricao;
        this.tempoEstimado = tempoEstimado;
        this.valorCusto = valorCusto;
        this.valorServico = valorServico;
        this.ativo = ativo;
    }

}
