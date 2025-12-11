package com.kronoly.Entity.Enuns;

public enum StatusAgendaEnum {
    ABERTA(1, "ABERTA"),
    ALMOCO(2,"ALMOÇO"),
    LOTADA(3, "LOTADA"),
    EM_MANUTENCAO(4, "EM MANUTENÇÃO"),
    FECHADA(5, "FECHADA"),
    INATIVA(6, "INATIVA");



    private int id;
    private String descricao;

    StatusAgendaEnum(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    // Sobrescrita do metodo toString para retorno da descrição
    @Override
    public String toString() {
        return this.descricao;
    }
}
