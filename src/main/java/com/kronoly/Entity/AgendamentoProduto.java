package com.kronoly.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AGENDAMENTO_PRODUTO")
public class AgendamentoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_AGENDAMENTO")
    private Agendamento agendamento;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO")
    private Produto produto;


    @Column(name = "valorCusto")
    private double valorCusto;

    @Column(name = "valorVenda")
    private double valorVenda;

    @Column(name = "QUANTIDADE_PRODUTOS")
    private int quantProdutos;




}

