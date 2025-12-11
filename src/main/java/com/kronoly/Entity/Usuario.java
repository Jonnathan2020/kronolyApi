package com.kronoly.Entity;

import com.kronoly.Entity.Enuns.TipoUsuarioEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TBL_USUARIO")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private int idUsuario;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "TELEFONE")
    private int telefone;

    //@JoinColumn(name = "CONTATO")
    //@OneToOne
   // private Contato contato;

    @JoinColumn(name = "ENDERECO")
    @OneToOne
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_USUARIO")
    private TipoUsuarioEnum tipoUsuario;


    //nivel de permissao do usuario
    //@Column(name = "ROLES")
    //private RoleEnum role;

//
//
//    //metodos implementados do userdatails para segurança de autenticacao
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if (this.getRole() == RoleEnum.ADMIN){
//            return List.of(
//                    new SimpleGrantedAuthority("ROLE_ADMIN"),
//                    new SimpleGrantedAuthority("ROLE_USER")
//            );
//        }
//        return List.of(
//                new SimpleGrantedAuthority("ROLE_USER")
//        );
//    }
//
//    @Override
//    public String getPassword() {
//        return this.getSenha();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

    /*IDs compartilhados significam que tanto a Empresa quanto
     o Prestador usam o mesmo ID da classe Usuario, já que
     ambos herdam dessa classe. Quando você registra um
     Pagamento, o campo id_usuario pode ser o ID de uma
     Empresa ou de um Prestador, mas o valor do ID será
     o mesmo para ambos. A tabela Pagamento armazena
     esse ID compartilhado, independentemente do tipo
     de usuário. Isso permite que tanto empresas quanto
     prestadores possam estar associados ao pagamento com
     o mesmo ID. Ou seja, o pagamento pode se referir a
     diferentes tipos de usuários usando o mesmo campo de ID.*/
}
