package com.kronoly.DTO;

import com.kronoly.Entity.Enuns.TipoUsuarioEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDTO {

    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private int telefone;
    private TipoUsuarioEnum tipoUsuario;
    private String caminhoFoto;

}

