package com.kronoly.Controller;

import com.kronoly.DTO.AgendaResponseDTO;
import com.kronoly.DTO.HorarioResponseDTO;
import com.kronoly.Entity.Horario;
import com.kronoly.Service.HorarioService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public ResponseEntity<List<HorarioResponseDTO>> consultarHorarios(){
        List<HorarioResponseDTO> lista = horarioService.consultarHorarios();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<HorarioResponseDTO>> consultarHorariosPorDisponibilidade(){
        return ResponseEntity.ok(horarioService.consultarHorariosPorDisponibilidade());
    }

    @GetMapping("/indisponiveis")
    public ResponseEntity<List<HorarioResponseDTO>> consultarHorariosPorIndisponibilidade(){
        return ResponseEntity.ok(horarioService.consultarHorariosPorIndisponibilidade());
    }

    @GetMapping("/disponiveis/{data}")
    public ResponseEntity<List<HorarioResponseDTO>> consultarHorariosPorDataEDisponibilidade(@PathVariable LocalDate data){
        return ResponseEntity.ok(horarioService.consultarHorariosPorDataEDisponibilidade(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioResponseDTO> consultarHorarioPorId(@PathVariable int id){
        return ResponseEntity.ok(horarioService.consultarHorarioPorId(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioResponseDTO> alterarDisponibilidade(@PathVariable int id){

        return ResponseEntity.ok(horarioService.alterarDisponibilidadePorId(id));
    }

    @PutMapping("/{data}/{horaInicio}")
    public ResponseEntity<HorarioResponseDTO> alterarDisponibilidadePorDataEHora(@PathVariable LocalDate data,@PathVariable LocalTime horaInicio){

        return ResponseEntity.ok(horarioService.alterarDisponibilidadePorDataEHora(data, horaInicio));
    }

    //Para requisição e verificação do funcionamento no service
    @PutMapping("/bloquearHorarios/{dataInicio}/{dataFim}")
    public ResponseEntity<List<HorarioResponseDTO>> bloquearHorariosDoAgendamento(@PathVariable LocalDateTime dataInicio, @PathVariable LocalDateTime dataFim){
        return ResponseEntity.ok(horarioService.bloquearHorariosDoAgendamento(dataInicio, dataFim));
    }
}
