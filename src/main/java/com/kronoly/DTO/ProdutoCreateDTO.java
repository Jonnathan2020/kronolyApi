package com.kronoly.DTO;

import com.kronoly.Entity.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoCreateDTO {

    private int idProduto;
    private String descricao;
    private double valorCusto;
    private double valorVenda;
}