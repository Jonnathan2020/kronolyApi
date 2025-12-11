package com.kronoly.Controller;

import com.kronoly.DTO.AgendamentoCreateDTO;
import com.kronoly.DTO.AgendamentoDTO;
import com.kronoly.DTO.AgendamentoUpdateDTO;
import com.kronoly.Entity.Agendamento;
import com.kronoly.Service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping("/registro")
    public ResponseEntity<AgendamentoDTO> cadastrarAgendamento(@RequestBody AgendamentoCreateDTO agendamentoCreateDTO){
        AgendamentoDTO agendamentoCriado  = agendamentoService.cadastrarAgendamento(agendamentoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoCriado);
    }

    @GetMapping
    public List<AgendamentoDTO> consultarAgendamento(){
        return agendamentoService.consultarAgendamentos();
    }

    @GetMapping("/{id}")
    public AgendamentoDTO consultarPorId(@PathVariable int id){
        return agendamentoService.consultarAgendamentoPorId(id);
    }

    @GetMapping("/busca/empresa/{idEmpresa}")
    public List<AgendamentoDTO> consultarPoridEmpresa(@PathVariable int idEmpresa){
        return agendamentoService.consultarAgendamentosPorEmpresa(idEmpresa);
    }

    @GetMapping("/busca/cliente/{idCliente}")
    public List<AgendamentoDTO> consultarPorIdCliente(@PathVariable int idCliente){
        return agendamentoService.consultarAgendamentosPorCliente(idCliente);
    }

    @PutMapping("/{id}")
    public Agendamento alterarAgendamento(@RequestBody AgendamentoUpdateDTO agendamentoUpdateDTO, @PathVariable int id){
        if(id == agendamentoUpdateDTO.getIdAgendamento()){
            return agendamentoService.alterarAgendamento(id, agendamentoUpdateDTO);
        }
        else
            return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        agendamentoService.delete(id);
    }

    @PutMapping("/{id}/confirmarAgendamento")
    public AgendamentoDTO confirmarAgendamento(int id){
        return agendamentoService.confirmarAgendamento(id);
    }

    @PutMapping("/{id}/iniciarAgendamento")
    public AgendamentoDTO iniciarAgendamento(int id){
        return agendamentoService.iniciarAgendamento(id);
    }

    @PutMapping("/{id}/finalizarAgendamento")
    public AgendamentoDTO finalizarAgendamento(int id){
        return agendamentoService.finalizarAgendamento(id);
    }

    @PutMapping("/{id}/cancelarAgendamentoPrestador")
    public AgendamentoDTO cancelarAgendamentoPrestador(int id){
        return agendamentoService.cancelarAgendamentoPrestador(id);
    }

    @PutMapping("/{id}/cancelarAgendamentoCliente")
    public AgendamentoDTO cancelarAgendamentoCliente(int id){
        return agendamentoService.cancelarAgendamentoCliente(id);
    }

    @PutMapping("/{id}/clienteAusente")
    public AgendamentoDTO clienteAusente(int id){
        return agendamentoService.reportarClienteAusente(id);
    }
}
