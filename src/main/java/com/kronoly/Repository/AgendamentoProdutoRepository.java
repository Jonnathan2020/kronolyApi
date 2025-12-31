package com.kronoly.Repository;

import com.kronoly.Entity.Agendamento;
import com.kronoly.Entity.AgendamentoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoProdutoRepository extends JpaRepository<AgendamentoProduto, Integer> {
    void deleteByAgendamento(Agendamento agendamentoExistente);
}
