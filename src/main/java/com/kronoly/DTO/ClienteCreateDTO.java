package com.kronoly.DTO;

import com.kronoly.Entity.Agendamento;
import com.kronoly.Entity.Empresa;
import com.kronoly.Entity.Endereco;
import com.kronoly.Entity.Enuns.StatusUsuarioEnum;
import com.kronoly.Entity.Enuns.UfEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClienteCreateDTO {

    private String documento;
    public String nome;
    public int telefone;
    public String imgPerfil;
    public StatusUsuarioEnum statusUsuarioEnum;
    private Empresa empresa;

    private String logradouro;
    private int numero;
    private String bairro;
    private String cidade;
    private UfEnum uf;
    private int cep;
    private String complemento;

}
