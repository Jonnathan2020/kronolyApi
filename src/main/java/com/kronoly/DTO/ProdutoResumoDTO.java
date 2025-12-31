package com.kronoly.DTO;

import com.kronoly.Entity.AgendamentoProduto;
import com.kronoly.Entity.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoResumoDTO {

    private int idProduto;
    private String descricao;
    private int quantProduto;
    private double valorCusto;
    private double valorVenda;


    // ✅ Construtor que recebe a entidade Produto
    public ProdutoResumoDTO(Produto produto) {
        this.idProduto = produto.getIdProduto();
        this.descricao = produto.getDescricao();
        this.valorCusto = produto.getValorCusto();
        this.valorVenda = produto.getValorVenda();
    }

    public ProdutoResumoDTO(int idProduto, String descricao, double valorCusto, double valorVenda) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.valorCusto = valorCusto;
        this.valorVenda = valorVenda;
    }

    public ProdutoResumoDTO(AgendamentoProduto ap) {
        this.idProduto = ap.getProduto().getIdProduto();
        this.descricao = ap.getProduto().getDescricao();
        this.valorCusto = ap.getValorCusto();
        this.valorVenda = ap.getValorVenda();
    }
}