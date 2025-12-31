package com.kronoly.DTO;

import com.kronoly.Entity.AgendamentoServico;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgendamentoServicoResumoDTO {

    private int idServico;
    private String descricao;
    private Long tempoEstimado;
    private double valorCusto;
    private double valorServico;
    private int quantServicos;

    public AgendamentoServicoResumoDTO(AgendamentoServico as) {
        this.idServico = as.getServico().getIdServico();
        this.descricao = as.getServico().getDescricao();
        this.quantServicos = as.getQuantServicos();
        this.tempoEstimado = as.getTempoEstimado();
        this.valorServico = as.getValorServico();
    }
}
