package com.kronoly.Controller;

import com.kronoly.DTO.EmpresaCreateDTO;
import com.kronoly.DTO.EmpresaUpdateDTO;
import com.kronoly.Entity.Empresa;
import com.kronoly.Service.EmpresaService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping("/registro")
    public ResponseEntity<Empresa> cadastrarEmpresa(@RequestBody EmpresaCreateDTO empresaCreateDTO){
        Empresa empresaCriada = empresaService.cadastrarEmpresa(empresaCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaCriada);
    }

    @GetMapping
    public List<Empresa> listarEmpresa(){
        return empresaService.consultarEmpresas();
    }

    @GetMapping("/{id}")
    public Empresa consultarEmpresaPorId(@PathVariable int id){
        return empresaService.consultarEmpresaPorId(id);
    }

    @GetMapping("/busca/nome/{nomeFantasia}")
    public List<Empresa> consultarEmpresaPorNome(@PathVariable String nomeFantasia){
        return empresaService.consultarEmpresaPorNomeFantasia(nomeFantasia);
    }

    @GetMapping("/busca/razao/{razaoSocial}")
    public List<Empresa> consultarEmpresaPorRazaoSocial(@PathVariable String razaoSocial){
        return empresaService.consultarEmpresaPorRazaoSocial(razaoSocial);
    }

    @GetMapping("/busca/documento/{documento}")
    public List<Empresa> consultarEmpresaPorDocumento(@PathVariable String documento){
        return empresaService.consultarEmpresaPorDocumento(documento);
    }

    @PutMapping("/{id}")
    public Empresa alterarEmpresa(@RequestBody EmpresaUpdateDTO empresaUpdateDTO,@PathVariable int id){
        if (id == empresaUpdateDTO.getIdEmpresa()){
            return empresaService.alterarEmpresa(id, empresaUpdateDTO);
        }
        else
            return null;

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){

        empresaService.delete(id);
    }

}
