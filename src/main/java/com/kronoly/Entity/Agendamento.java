package com.kronoly.Entity;

import com.kronoly.Entity.Enuns.FormaPagtoEnum;
import com.kronoly.Entity.Enuns.StatusAgendamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "TBL_AGENDAMENTO")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDAMENTO")
    private int idAgendamento;

    @Column(name = "TITULO")
    public String titulo;

    @Column(name = "DESCRICAO")
    public String descricao;

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servico> servicos = new ArrayList<>();

    @Column(name = "DATA_INICIO")
    public LocalDateTime dataInicio;

    @Column(name = "DATA_FIM")
    public LocalDateTime dataFim;

    @Column(name = "NOME_CLIENTE")
    public String nomeCliente;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA")
    public Empresa empresa;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_AGENDAMENTO")
    private StatusAgendamento statusAgendamento;

    @Column(name = "VALOR_SERVICOS")
    public double valorServicos;

    @Column(name = "VALOR_PRODUTOS")
    public double valorProdutos;

    @Enumerated(EnumType.STRING)
    @Column(name = "FORMA_PAGTO")
    public FormaPagtoEnum formaPagtoEnum;


}
