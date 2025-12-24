package com.kronoly.Service;

import com.kronoly.DTO.*;
import com.kronoly.Entity.Produto;
import com.kronoly.Entity.Servico;
import com.kronoly.Repository.ProdutoRepository;
import com.kronoly.Repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {


    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoResumoDTO cadastrarProduto(ProdutoCreateDTO produtoCreateDTO){

        Produto produto = new Produto();

        produto.setDescricao(produtoCreateDTO.getDescricao());
        produto.setValorCusto(produtoCreateDTO.getValorCusto());
        produto.setValorVenda(produtoCreateDTO.getValorVenda());

        return new ProdutoResumoDTO(produtoRepository.save(produto));
    }

    public List<ProdutoResumoDTO> consultarProdutos(){
        List<Produto> produtos = produtoRepository.findAll();

        if (produtos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum produto encontrado!");
        }

        return produtos.stream().map(produto -> new ProdutoResumoDTO(
                        produto.getIdProduto(),
                        produto.getDescricao(),
                        produto.getValorCusto(),
                        produto.getValorVenda()
                )
        ).collect(Collectors.toList());
    }

    public ProdutoResumoDTO consultarProdutoPorId(int id){
        Produto produtoExistente = produtoRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Nenhum produto encontrado!"));

        return new ProdutoResumoDTO(produtoExistente);
    }

    public List<ProdutoResumoDTO> consultarProdutoPorDescricao(String descricao){
        List<Produto> produtoExistente = produtoRepository.findByDescricaoIgnoreCaseContaining(descricao);

        if (produtoExistente.isEmpty()) {
            throw new IllegalArgumentException("Nenhum produto encontrado!");
        }

        return produtoExistente.stream().map(produto -> new ProdutoResumoDTO(
                        produto.getIdProduto(),
                        produto.getDescricao(),
                        produto.getValorCusto(),
                        produto.getValorVenda()
                )
        ).collect(Collectors.toList());
    }

    public ProdutoResumoDTO alterarProduto(int idProduto, ProdutoUpdateDTO produtoUpdateDTO){
                Produto produtoExistente = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado!!!"));

        if (produtoUpdateDTO.getDescricao() != null){
            produtoExistente.setDescricao(produtoUpdateDTO.getDescricao());
        }
        if(produtoUpdateDTO.getValorCusto() != 0){
            produtoExistente.setValorCusto(produtoUpdateDTO.getValorCusto());
        }
        if(produtoUpdateDTO.getValorVenda() !=0){
            produtoExistente.setValorVenda(produtoUpdateDTO.getValorVenda());
        }

        produtoRepository.save(produtoExistente);
        return new ProdutoResumoDTO(produtoExistente);

    }

    public void deleteProduto(int idProduto){
        produtoRepository.deleteById(idProduto);
    }
}