package com.kronoly.Repository;

import com.kronoly.Entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {
    List<Agendamento> findByEmpresa_idEmpresa(int idEmpresa);
    List<Agendamento> findByDataInicioBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );
    List<Agendamento> findByCliente_idCliente(int idCliente);
}
