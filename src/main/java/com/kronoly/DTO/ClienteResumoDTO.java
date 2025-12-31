package com.kronoly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResumoDTO {
    private int idCliente;
    private String documento;
    public String nome;
    public Long telefone;

    public ClienteResumoDTO(int idCliente, String documento, String nome, Long telefone) {
        this.idCliente = idCliente;
        this.documento = documento;
        this.nome = nome;
        this.telefone = telefone;
    }
}
