package com.kronoly.DTO;

import com.kronoly.Entity.Enuns.StatusUsuarioEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaResumoDTO {

    private int idEmpresa;
    private String documento;
    private String razaoSocial;
    private String nomeFantasia;
    private String representante;
    private String logo;
    private StatusUsuarioEnum statusEmpresaEnum;

    public EmpresaResumoDTO(int idEmpresa, String documento, String razaoSocial, String nomeFantasia,
                            String representante, String logo, StatusUsuarioEnum statusEmpresaEnum) {
        this.idEmpresa = idEmpresa;
        this.documento = documento;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.representante = representante;
        this.logo = logo;
        this.statusEmpresaEnum = statusEmpresaEnum;
    }

    // Construtor prático para converter direto da entidade Empresa
    public EmpresaResumoDTO(com.kronoly.Entity.Empresa empresa) {
        this.idEmpresa = empresa.getIdEmpresa();
        this.documento = empresa.getDocumento();
        this.razaoSocial = empresa.getRazaoSocial();
        this.nomeFantasia = empresa.getNomeFantasia();
    }
}