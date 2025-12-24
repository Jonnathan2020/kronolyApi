package com.kronoly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoCreateDTO {

    private String descricao;
    private Long tempoEstimado;
    private double valorCusto;
    private double valorServico;
}
