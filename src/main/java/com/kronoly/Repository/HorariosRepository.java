package com.kronoly.Repository;

import com.kronoly.Entity.Horarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorariosRepository extends JpaRepository<Integer, Horarios> {
}
