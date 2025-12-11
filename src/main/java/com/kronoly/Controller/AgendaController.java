package com.kronoly.Controller;

import com.kronoly.DTO.AgendaCreateDTO;
import com.kronoly.DTO.AgendaUpdateDTO;
import com.kronoly.Entity.Agenda;
import com.kronoly.Service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @PostMapping("/registro")
    public ResponseEntity<Agenda> cadastrarAgenda(@RequestBody AgendaCreateDTO agendaCreateDTO){
        Agenda agendaCriada = agendaService.cadastrarAgenda(agendaCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaCriada);
    }

    @GetMapping
    public List<Agenda> consultarAgenda(){
        return agendaService.consultarAgendas();
    }

    @GetMapping("/{id}")
    public Agenda consultarPorId(@PathVariable int id){
        return agendaService.consultarAgendaPorId(id);
    }

    @GetMapping("/busca/{idEmpresa}")
    public List<Agenda> consultarAgendaPorEmpresa(@PathVariable int idEmpresa){
        return agendaService.consultarAgendasPorEmpresa(idEmpresa);
    }

    @PutMapping("/{id}")
    public Agenda alterarAgenda(@RequestBody AgendaUpdateDTO agendaUpdateDTO, @PathVariable int id){
        if(id == agendaUpdateDTO.getIdAgenda()){
            return agendaService.alterarAgenda(id, agendaUpdateDTO);
        }
        else
            return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        agendaService.delete(id);
    }
}
