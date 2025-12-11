package com.kronoly.Repository;

import com.kronoly.Entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    List<Empresa> findByNomeFantasiaIgnoreCaseContaining(String nomeFantasia);
    List<Empresa> findByRazaoSocialIgnoreCaseContaining(String razaoSocial);
    List<Empresa> findByDocumentoIgnoreCaseContaining(String documento);

}
