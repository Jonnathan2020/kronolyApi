package com.kronoly.Repository;

import com.kronoly.Entity.Produto;
import com.kronoly.Entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByDescricaoIgnoreCaseContaining(String descricao);
}
