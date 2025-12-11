package com.kronoly.DTO;

import com.kronoly.Entity.Enuns.TipoUsuarioEnum;
import com.kronoly.Entity.Enuns.UfEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDTO {

    private String nome;
    private String email;
    private String senha;
    private int telefone;
    private TipoUsuarioEnum tipoUsuario;


    private String logradouro;
    private int numero;
    private String bairro;
    private String cidade;
    private UfEnum uf;
    private int cep;
    private String complemento;

}