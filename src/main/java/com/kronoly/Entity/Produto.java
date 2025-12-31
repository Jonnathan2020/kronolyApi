package com.kronoly.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "TBL_PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO")
    private int idProduto;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "VALOR_CUSTO")
    private double valorCusto;

    @Column(name = "VALOR_VENDA")
    private double valorVenda;

    @OneToMany(mappedBy = "produto")
    private List<AgendamentoProduto> agendamentos = new ArrayList<>();


}
