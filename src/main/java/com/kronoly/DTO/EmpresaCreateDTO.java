package com.kronoly.DTO;

import com.kronoly.Entity.Endereco;
import com.kronoly.Entity.Enuns.StatusUsuarioEnum;
import com.kronoly.Entity.Enuns.UfEnum;
import com.kronoly.Entity.HorariosDisponiveis;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmpresaCreateDTO {

    private String documento;
    public String razaoSocial;
    public String nomeFantasia;
    public String representante;
    public String logo;
    public StatusUsuarioEnum statusEmpresaEnum;

    private String logradouro;
    private int numero;
    private String bairro;
    private String cidade;
    private UfEnum uf;
    private int cep;
    private String complemento;
}
