package com.kronoly.Entity;

import com.kronoly.Entity.Enuns.StatusUsuarioEnum;
import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "TBL_CLIENTE")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private int idCliente;

    @Column(name = "DOCUMENTO")
    private String documento;

    @Column(name = "NOME")
    public String nome;

    @Column(name = "TELEFONE")
    private Long telefone;

    @JoinColumn(name = "ENDERECO")
    @OneToOne
    public Endereco endereco;

    @Column(name = "IMG_PERFIL")
    public String imgPerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_USUARIO")
    public StatusUsuarioEnum statusUsuarioEnum;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA")
    private Empresa empresa;


    public Object stream() {
        return null;
    }
}
