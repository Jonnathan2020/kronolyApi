package com.kronoly.DTO;

import com.kronoly.Entity.Agenda;
import com.kronoly.Entity.Empresa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class HorarioResponseDTO {

    private int idHorario;

    private LocalDate data; // Ex: 2026-01-12

    private LocalTime horaInicio; // Ex: 08:00

    private LocalTime horaFim; // Ex: 08:30 (Calculado com duracaoSlot)

    private boolean disponivel;

    private AgendaResponseDTO agendaResponseDTO;

    private EmpresaResumoDTO empresaResumoDTO;
}
