package com.kronoly.Controller;

import com.kronoly.DTO.AgendamentoDTO;
import com.kronoly.DTO.ServicoCreateDTO;
import com.kronoly.DTO.ServicoResumoDTO;
import com.kronoly.DTO.ServicoUpdateDTO;
import com.kronoly.Service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @PostMapping("/registro")
    public ResponseEntity<ServicoResumoDTO> cadastrarServico(@RequestBody ServicoCreateDTO servicoCreateDTO){
        ServicoResumoDTO servicoCriado  = servicoService.cadastrarServico(servicoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoCriado);
    }


    @GetMapping()
    public List<ServicoResumoDTO> consultarServicos(){
        return servicoService.consultarServicos();
    }

    @GetMapping("/consultaId/{id}")
    public ServicoResumoDTO consultarServicoPorId(@PathVariable int id){
        return servicoService.consultarServicoPorId(id);
    }

    @GetMapping("/consultaDesc/{descricao}")
    public List<ServicoResumoDTO> consultarServicoPorDescricao(@PathVariable String descricao){
        return servicoService.consultarServicoPorDescricao(descricao);
    }

    @PutMapping("/{id}")
    public ServicoResumoDTO alterarServico(@PathVariable int id, @RequestBody ServicoUpdateDTO servicoUpdateDTO){
        if(id == servicoUpdateDTO.getIdServico()){
            return servicoService.alterarServico(id, servicoUpdateDTO);
        }
        else
            return null;
    }

    @DeleteMapping("/{id}")
    public void deletarServico(@PathVariable int id){
        servicoService.deleteServico(id);
    }
}
