package com.kronoly.Controller;

import com.kronoly.DTO.*;
import com.kronoly.Entity.Produto;
import com.kronoly.Service.ProdutoService;
import com.kronoly.Service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/registro")
    public ResponseEntity<ProdutoResumoDTO> cadastrarProduto(@RequestBody ProdutoCreateDTO produtoCreateDTO){
        ProdutoResumoDTO produtoCriado  = produtoService.cadastrarProduto(produtoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }


    @GetMapping()
    public List<ProdutoResumoDTO> consultarProdutos(){
        return produtoService.consultarProdutos();
    }

    @GetMapping("/consulta/id/{id}")
    public ProdutoResumoDTO consultarProdutoPorId(@PathVariable int id){
        return produtoService.consultarProdutoPorId(id);
    }

    @GetMapping("/consulta/descricao/{descricao}")
    public List<ProdutoResumoDTO> consultarProdutoPorDescricao(@PathVariable String descricao){
        return produtoService.consultarProdutoPorDescricao(descricao);
    }

    @PutMapping("/{id}")
    public ProdutoResumoDTO alterarServico(@PathVariable int id, @RequestBody ProdutoUpdateDTO produtoUpdateDTO){
        if(id == produtoUpdateDTO.getIdProduto()){
            return produtoService.alterarProduto(id,produtoUpdateDTO);
        }
        else
            return null;
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable int id){
        produtoService.deleteProduto(id);
    }
}

