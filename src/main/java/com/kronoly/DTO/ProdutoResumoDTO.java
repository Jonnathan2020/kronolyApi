package com.kronoly.DTO;

import com.kronoly.Entity.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoResumoDTO {

    private int idProduto;
    private String descricao;
    private double valorCusto;
    private double valorVenda;

    // ✅ Construtor que recebe a entidade Produto
    public ProdutoResumoDTO(Produto produto) {
        this.idProduto = produto.getIdProduto();
        this.descricao = produto.getDescricao();
        this.valorCusto = produto.getValorCusto();
        this.valorVenda = produto.getValorVenda();
    }
}