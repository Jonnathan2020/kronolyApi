package com.kronoly.DTO;

import com.kronoly.Entity.Enuns.StatusUsuarioEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClienteDTO {

    private int idCliente;
    private String documento;
    private String nome;
    private Long telefone;
    private String imgPerfil;
    private StatusUsuarioEnum statusUsuarioEnum;

    // Dados resumidos de endereço
    private EnderecoResumoDTO endereco;

    // Dados resumidos da empresa vinculada
    private EmpresaResumoDTO empresa;

    // Lista de agendamentos em formato leve
    private List<AgendamentoResumoDTO> agendamentos;
}
