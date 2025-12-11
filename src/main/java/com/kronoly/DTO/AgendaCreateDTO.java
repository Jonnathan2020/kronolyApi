package com.kronoly.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kronoly.Entity.Empresa;
import com.kronoly.Entity.Enuns.StatusAgendaEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AgendaCreateDTO {
    private int idAgenda;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public LocalDateTime horaAlmoco;
    public StatusAgendaEnum statusAgendaEnum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public LocalDateTime horaAbertura;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public LocalDateTime horaFechamento;
    public Empresa empresa;

}
