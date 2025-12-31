package com.kronoly.Repository;

import com.kronoly.Entity.Agendamento;
import com.kronoly.Entity.AgendamentoProduto;
import com.kronoly.Entity.AgendamentoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoServicoRepository extends JpaRepository<AgendamentoServico, Integer> {
    void deleteByAgendamento(Agendamento agendamentoExistente);
}
