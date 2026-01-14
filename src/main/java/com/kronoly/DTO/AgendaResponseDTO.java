package com.kronoly.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class AgendaResponseDTO {
    private int idAgenda;
    public LocalDate dataInicio;
    public LocalDate dataFinal;
    private LocalTime horaAbertura;
    public LocalTime horaAlmoco;
    public LocalTime horaRetornoAlmoco;
    private LocalTime horaFechamento;
    private Integer duracaoSlot;
    private List<DayOfWeek> diasSemana;
    private EmpresaResumoDTO empresa; // Aqui entra o resumo
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}

