package com.kronoly.Service;

import com.kronoly.DTO.UsuarioCreateDTO;
import com.kronoly.DTO.UsuarioUpdateDTO;
import com.kronoly.Entity.Endereco;
import com.kronoly.Entity.Usuario;
import com.kronoly.Repository.EnderecoRepository;
import com.kronoly.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    //consultar usuarios
    public List<Usuario> consultarUsuarios(){
        return usuarioRepository.findAll();                //chama o metodo da biblioteca jparepository pre-definido pela framework
    }

    // Expressão regular para validar e-mails
    // permite que antes do @ tenha letras e numeros, depois dominio e por fim extensao limitada
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    //metodo que faz validacao do email para verificar se sintaxe esta correta
    public boolean isEmailValido(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public Usuario cadastrarUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        // Criação do usuário
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioCreateDTO.getNome());
        usuario.setEmail(usuarioCreateDTO.getEmail());
        usuario.setSenha(usuarioCreateDTO.getSenha());
        usuario.setTelefone(usuarioCreateDTO.getTelefone());
        usuario.setTipoUsuario(usuarioCreateDTO.getTipoUsuario());

        // Criação do endereço com base nos dados do DTO
        Endereco endereco = new Endereco();
        endereco.setLogradouro(usuarioCreateDTO.getLogradouro());
        endereco.setNumero(usuarioCreateDTO.getNumero());
        endereco.setBairro(usuarioCreateDTO.getBairro());
        endereco.setCidade(usuarioCreateDTO.getCidade());
        endereco.setUf(usuarioCreateDTO.getUf());
        endereco.setCep(usuarioCreateDTO.getCep());
        endereco.setComplemento(usuarioCreateDTO.getComplemento());

        // Salva o novo endereço no banco
        endereco = enderecoRepository.save(endereco);

        // Associa o endereço ao usuário
        usuario.setEndereco(endereco);

        // Salva o novo usuário no banco de dados
        return usuarioRepository.save(usuario);
    }

    //alterar informaçoes do usuario
    public Usuario alterarUsuario(int idUsuario, UsuarioUpdateDTO usuarioUpdateDTO){
        Usuario usuarioExistente = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!!"));

        if(usuarioUpdateDTO.getNome() != null){
            usuarioExistente.setNome(usuarioUpdateDTO.getNome());
        }
        if(usuarioUpdateDTO.getSenha() != null){
            usuarioExistente.setSenha(usuarioUpdateDTO.getSenha());
        }
        if(usuarioUpdateDTO.getTelefone() != 0){
            usuarioExistente.setTelefone(usuarioUpdateDTO.getTelefone());
        }

        if(usuarioUpdateDTO.getTipoUsuario() != null){
            usuarioExistente.setTipoUsuario(usuarioUpdateDTO.getTipoUsuario());
        }

        //retornou um metodo que salva as informações no banco
        return usuarioRepository.save(usuarioExistente);
    }

    //deletar usuario pelo id
    public void delete(int idUsuario){
        usuarioRepository.deleteById(idUsuario);           //metodo que faz o delete do usuario

    }

    public List<Usuario> consultarUsuarioPorNome(String nome) {
        return usuarioRepository.findByNomeIgnoreCaseContaining(nome);
    }

    //consultar usuario especifico
    public Usuario consultarUsuarioPorId(int idUsuario) {
        Usuario usuarioEspecifico = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado!!"));

        return usuarioEspecifico;
    }
}
