package com.kronoly.Entity.Enuns;

public enum TipoUsuarioEnum {

    ADMIN(1, "ADMIN"),
    EMPRESA(2, "EMPRESA"),
    PRESTADOR(3, "PRESTADOR"),
    CLIENTE(4, "CLIENTE");

    private int id;
    private String descricao;

    TipoUsuarioEnum(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    // Sobrescrita do metodo toString para retorno da descrição
    @Override
    public String toString() {
        return this.descricao;
    }
}
