package com.kronoly.DTO;

import com.kronoly.Entity.Enuns.StatusUsuarioEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteUpdateDTO {
    private int idCliente;
    public String nome;
    public String imgPerfil;
    public StatusUsuarioEnum statusUsuarioEnum;
    public Long telefone;

}
