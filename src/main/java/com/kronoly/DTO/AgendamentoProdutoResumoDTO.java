package com.kronoly.DTO;

import com.kronoly.Entity.AgendamentoProduto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgendamentoProdutoResumoDTO {

    private int idProduto;
    private String descricao;
    private int quantProduto;
    private double valorCusto;
    private double valorVenda;

    public AgendamentoProdutoResumoDTO(AgendamentoProduto ap) {
        this.idProduto = ap.getProduto().getIdProduto();
        this.descricao = ap.getProduto().getDescricao();
        this.quantProduto = ap.getQuantProdutos();
        this.valorCusto = ap.getValorCusto();
        this.valorVenda = ap.getValorVenda();
    }
}
