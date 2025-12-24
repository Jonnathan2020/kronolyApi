package com.kronoly.Repository;

import com.kronoly.Entity.Servico;
import com.kronoly.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    List<Servico> findByDescricaoIgnoreCaseContaining(String descricao);
}
