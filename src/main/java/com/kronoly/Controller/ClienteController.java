package com.kronoly.Controller;

import com.kronoly.DTO.ClienteCreateDTO;
import com.kronoly.DTO.ClienteDTO;
import com.kronoly.DTO.ClienteUpdateDTO;
import com.kronoly.Entity.Cliente;
import com.kronoly.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    //cadastrar cliente
    @PostMapping("/registro")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteCreateDTO clienteCreateDTO){
        Cliente clienteCriado = clienteService.cadastrarCliente(clienteCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }

    //listar todos clientes
    @GetMapping
    public List<ClienteDTO> listarClientes(){
        return clienteService.consultarClientes();
    }

    //listar cliente pelo id
    @GetMapping("/{id}")
    public Cliente listarClientePorId(@PathVariable int id){
        return clienteService.consultarClientePorId(id);
    }


    //consultar clientes pelo nome
    @GetMapping("/nome/{nome}")
    public List<Cliente> listarClientePorNome(@PathVariable String nome){
        return clienteService.consultarClientePorNome(nome);
    }

    @PutMapping("/{id}")
    public Cliente alterarCliente(@RequestBody ClienteUpdateDTO clienteUpdateDTO, @PathVariable("id") int id){
        if(id == clienteUpdateDTO.getIdCliente()){
            return clienteService.alterarCliente(id, clienteUpdateDTO);
        }
        else
            return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        clienteService.delete(id);
    }


}

