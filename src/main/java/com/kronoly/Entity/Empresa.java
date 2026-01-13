package com.kronoly.Entity;

import com.kronoly.Entity.Enuns.StatusUsuarioEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "TBL_EMPRESA")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESA")
    private int idEmpresa;

    @Column(name = "DOCUMENTO")
    private String documento;

    @Column(name = "RAZAO_SOCIAL")
    public String razaoSocial;

    @Column(name = "nomeFantasia")
    public String nomeFantasia;

    @Column(name = "REPRESENTANTE")
    private String representante;

    //url da logo no próprio dispositivo salvo na pasta do app
    @Column(name = "LOGO")
    public String logo;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_EMPRESA")
    public StatusUsuarioEnum statusEmpresaEnum;

    @JoinColumn(name = "ENDERECO")
    @OneToOne
    private Endereco endereco;

    //@OneToMany(mappedBy = "TBL_EMPRESA", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Parceiro> parceiros = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cliente> clientes = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Horario> horarios = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agenda> agendas = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos = new ArrayList<>();
}
