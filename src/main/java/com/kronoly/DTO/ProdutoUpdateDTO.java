package com.kronoly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoUpdateDTO {

    private int idProduto;
    private String descricao;
    private int quantProduto;
    private double valorCusto;
    private double valorVenda;
}