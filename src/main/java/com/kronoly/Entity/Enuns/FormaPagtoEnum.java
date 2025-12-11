package com.kronoly.Entity.Enuns;

import lombok.Getter;

@Getter
public enum FormaPagtoEnum {
    DINHEIRO(1,"DINHEIRO"),
    DEBITO(2,"DEBITO"),
    CREDITO(2,"CREDITO"),
    PIX(2,"PIX"),
    BOLETO(2,"BOLETO");

    private int id;
    private String descricao;

    FormaPagtoEnum(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public String toString(){return this.descricao;}
}
