package com.kronoly.Entity.Enuns;

import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum StatusAgendamento{

    PENDENTE(1, "PENDENTE"),
    CONFIRMADO(2, "CONFIRMADO"),
    EM_ANDAMENTO(3,"EM ANDAMENTO"),
    CONCLUIDO(4, "CONCLUÍDO"),
    CANCELADO_CLIENTE(5, "CANCELADO PELO CLIENTE"),
    CANCELADO_PRESTADOR(6, "CANCELADO PELO PRESTADOR"),
    CLIENTE_AUSENTE(7, "CLIENTE AUSENTE");


    private int id;
    private String descricao;

    StatusAgendamento(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    // Sobrescrita do metodo toString para retorno da descrição
    @Override
    public String toString() {
        return this.descricao;
    }
}