package com.kronoly.Controller;

import com.kronoly.DTO.UsuarioCreateDTO;
import com.kronoly.DTO.UsuarioUpdateDTO;
import com.kronoly.Entity.Usuario;
import com.kronoly.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Listar usuario
    @GetMapping
    public List<Usuario> listarUsuario(){
        return usuarioService.consultarUsuarios();
    }

    //Buscar usuário por id
    @GetMapping("/{id}")
    public Usuario consultarUsuarioPorId(@PathVariable int id){
        return usuarioService.consultarUsuarioPorId(id);
    }

    //Buscar usuário pelo nome
    @GetMapping("/nome/{nome}")
    public List<Usuario> getUsuarioByName(@PathVariable String nome){
        return usuarioService.consultarUsuarioPorNome(nome);
    }

    //Alterar usuário
    @PutMapping("/{id}")
    public Usuario alterarUsuario(@RequestBody UsuarioUpdateDTO usuarioUpdateDTO, @PathVariable("id") int id){
        if(id == usuarioUpdateDTO.getIdUsuario()){
            return usuarioService.alterarUsuario(id, usuarioUpdateDTO);
        }
        else
            return null;
    }

    //Cadastrar usuário
    @PostMapping("/registro")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioCriado = usuarioService.cadastrarUsuario(usuarioCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        usuarioService.delete(id);
    }

}
