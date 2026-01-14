package com.kronoly.Repository;

import com.kronoly.Entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    List<Horario> findByDisponivel(boolean disponivel);
    List<Horario> findByDataAndDisponivel(LocalDate data, boolean disponivel);

    @Query(value = """
        SELECT *
        FROM TBL_HORARIOS_DISPONIVEIS
        WHERE DATA_DISPONIVEL = CAST(:data AS DATE)
        AND HORA_INICIO = CAST(:hora AS TIME)
    """, nativeQuery = true)
    Optional<Horario> findByDataAndHoraInicio(
            @Param("data") LocalDate data,
            @Param("hora") LocalTime hora
    );


    @Query(value = """
        SELECT *
        FROM TBL_HORARIOS_DISPONIVEIS
        WHERE DATA_DISPONIVEL = CAST(:data AS DATE)
        AND HORA_INICIO >= CAST(:horaInicio AS TIME)
        AND HORA_INICIO <  CAST(:horaFim AS TIME)
    """, nativeQuery = true)
    List<Horario> findHorariosNoIntervalo(
            @Param("data") LocalDate data,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim
    );

}
