package com.kronoly.Controller;

import com.kronoly.DTO.AgendaCreateDTO;
import com.kronoly.DTO.AgendaResponseDTO;
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
    public ResponseEntity<AgendaResponseDTO> cadastrarAgenda(@RequestBody AgendaCreateDTO agendaCreateDTO){
        // Chama a service que já salva a agenda, gera os horários e retorna o DTO com o resumo
        AgendaResponseDTO response = agendaService.cadastrarAgenda(agendaCreateDTO);

        // Retorna Status 201 (Created) e o corpo com os dados formatados
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AgendaResponseDTO>> consultarAgenda(){
        List<AgendaResponseDTO> lista = agendaService.consultarAgendas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponseDTO> consultarPorId(@PathVariable int id){
        return ResponseEntity.ok(agendaService.consultarAgendaPorId(id));
    }

    @GetMapping("/busca/{idEmpresa}")
    public ResponseEntity<List<AgendaResponseDTO>> consultarAgendaPorEmpresa(@PathVariable int idEmpresa){
        return ResponseEntity.ok(agendaService.consultarAgendasPorEmpresa(idEmpresa));

    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendaResponseDTO> alterarAgenda(
            @PathVariable int id,
            @RequestBody AgendaUpdateDTO agendaUpdateDTO) {

        // Validação de segurança: garante que o ID da URL é o mesmo que se deseja alterar
        // Em 2026, é comum permitir que o DTO não envie o ID, usando o da URL como soberano
        if (agendaUpdateDTO.getIdAgenda() != 0 && id != agendaUpdateDTO.getIdAgenda()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // A service agora retorna AgendaResponseDTO
            AgendaResponseDTO response = agendaService.alterarAgenda(id, agendaUpdateDTO);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Retorna 404 caso a service lance a exceção de "Agenda não encontrada"
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        agendaService.delete(id);
    }
}
