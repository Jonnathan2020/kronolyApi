package com.kronoly.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kronoly.Entity.Empresa;
import com.kronoly.Entity.Enuns.StatusAgendaEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AgendaCreateDTO {
    private int idAgenda;
    public int idEmpresa;
    public StatusAgendaEnum statusAgendaEnum;
    public LocalTime horaAbertura;
    public LocalTime horaAlmoco;
    public LocalTime horaRetornoAlmoco;
    public LocalTime horaFechamento;
    public LocalDate dataInicio;
    public LocalDate dataFinal;
    public LocalDateTime criadoEm;
    public LocalDateTime atualizadoEm;
    public int duracaoSlot;
    public List<DayOfWeek> diasSemana;

}
