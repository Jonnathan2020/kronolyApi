package com.kronoly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResumoDTO {
    private int idCliente;
    private String documento;
    public String nome;
    public int telefone;

    public ClienteResumoDTO(int idCliente, String documento, String nome, int telefone) {
        this.idCliente = idCliente;
        this.documento = documento;
        this.nome = nome;
        this.telefone = telefone;
    }
}
