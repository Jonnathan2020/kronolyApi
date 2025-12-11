package com.kronoly.Repository;

import com.kronoly.Entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    List<Agenda> findByEmpresa_idEmpresa(int idEmpresa);
}
