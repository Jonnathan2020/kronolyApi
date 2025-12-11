package com.kronoly.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kronoly.Entity.Cliente;
import com.kronoly.Entity.Empresa;
import com.kronoly.Entity.Enuns.FormaPagtoEnum;
import com.kronoly.Entity.Enuns.StatusAgendamento;
import com.kronoly.Entity.Produto;
import com.kronoly.Entity.Servico;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AgendamentoCreateDTO {

    public String titulo;
    public String descricao;
    private List<Produto> produtos = new ArrayList<>();
    private List<Servico> servicos = new ArrayList<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public LocalDateTime dataInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public LocalDateTime dataFim;
    public String nomeCliente;
    private int idCliente;
    public int idEmpresa;
    public double valorServicos;
    public double valorProdutos;
    public FormaPagtoEnum formaPagtoEnum;

}
