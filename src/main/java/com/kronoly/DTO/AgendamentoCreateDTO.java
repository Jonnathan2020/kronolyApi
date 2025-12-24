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
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AgendamentoCreateDTO {

    public String titulo;
    public String descricao;
    private List<Produto> produtos = new ArrayList<>();
    private List<Servico> servicos = new ArrayList<>();
    public LocalDate data;
    public LocalTime hora;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public LocalDateTime dataFim;
    public String nomeCliente;
    public int contatoCliente;
    private int idCliente;
    public int idEmpresa;
    public double valorServicos;
    public double valorProdutos;
    public FormaPagtoEnum formaPagtoEnum;

}

