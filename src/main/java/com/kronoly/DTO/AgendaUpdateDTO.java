package com.kronoly.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kronoly.Entity.Enuns.StatusAgendaEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AgendaUpdateDTO {
    private int idAgenda;
    public LocalTime horaAlmoco;
    public StatusAgendaEnum statusAgendaEnum;
    public LocalTime horaAbertura;
    public LocalTime horaFechamento;
    public int idEmpresa;
}
