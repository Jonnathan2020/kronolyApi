package com.kronoly.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResumoDTO {
    private int idEndereco;
    private int cep;
    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
}
