package com.kronoly.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kronoly.Entity.Enuns.FormaPagtoEnum;
import com.kronoly.Entity.Enuns.StatusAgendamento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AgendamentoResumoDTO {

    private int idAgendamento;
    private String titulo;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String nomeCliente;
    private String nomeEmpresa;
    private String statusAgendamento;
    private double valorTotal;
    private String formaPagto;

    // Construtor padrão (necessário para frameworks e serialização)
    public AgendamentoResumoDTO() {}

    // Construtor principal
    public AgendamentoResumoDTO(
            int idAgendamento,
            String titulo,
            String descricao,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
            LocalDateTime dataInicio,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
            LocalDateTime dataFim,
            String nomeCliente,
            String nomeEmpresa,
            String statusAgendamento,
            double valorTotal,
            String formaPagto
    ) {
        this.idAgendamento = idAgendamento;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.nomeCliente = nomeCliente;
        this.nomeEmpresa = nomeEmpresa;
        this.statusAgendamento = statusAgendamento;
        this.valorTotal = valorTotal;
        this.formaPagto = formaPagto;
    }

}
