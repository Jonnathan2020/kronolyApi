package com.kronoly.Service;

import com.kronoly.DTO.*;
import com.kronoly.Entity.Cliente;
import com.kronoly.Entity.Endereco;
import com.kronoly.Entity.Usuario;
import com.kronoly.Repository.ClienteRepository;
import com.kronoly.Repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente cadastrarCliente(ClienteCreateDTO clienteCreateDTO){
        Cliente cliente = new Cliente();
        cliente.setDocumento(clienteCreateDTO.getDocumento());
        cliente.setNome(clienteCreateDTO.getNome());
        cliente.setTelefone(clienteCreateDTO.getTelefone());
        cliente.setImgPerfil(clienteCreateDTO.getImgPerfil());
        // Criação do endereço com base nos dados do DTO
        Endereco endereco = new Endereco();
        endereco.setLogradouro(clienteCreateDTO.getLogradouro());
        endereco.setNumero(clienteCreateDTO.getNumero());
        endereco.setBairro(clienteCreateDTO.getBairro());
        endereco.setCidade(clienteCreateDTO.getCidade());
        endereco.setUf(clienteCreateDTO.getUf());
        endereco.setCep(clienteCreateDTO.getCep());
        endereco.setComplemento(clienteCreateDTO.getComplemento());

        // Salva o novo endereço no banco
        endereco = enderecoRepository.save(endereco);

        // Associa o endereço ao cliente
        cliente.setEndereco(endereco);

        return clienteRepository.save(cliente);

    }

    //alterar informaçoes do usuario
    public Cliente alterarCliente(int idCliente, ClienteUpdateDTO clienteUpdateDTO) {
        Cliente clienteExistente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!!"));

        if(clienteUpdateDTO.getNome() != null){
            clienteExistente.setNome(clienteUpdateDTO.getNome());
        }

        if(clienteUpdateDTO.getTelefone() != 0){
            clienteExistente.setTelefone(clienteUpdateDTO.getTelefone());
        }

        if(clienteUpdateDTO.getImgPerfil() != null){
            clienteExistente.setImgPerfil(clienteUpdateDTO.getImgPerfil());
        }

        if(clienteUpdateDTO.getStatusUsuarioEnum() != null){
            clienteExistente.setStatusUsuarioEnum(clienteUpdateDTO.getStatusUsuarioEnum());
        }

        return clienteRepository.save(clienteExistente);
    }

    //consultar clientes
    public List<ClienteDTO> consultarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream().map(cliente -> {
            ClienteDTO dto = new ClienteDTO();
            dto.setIdCliente(cliente.getIdCliente());
            dto.setDocumento(cliente.getDocumento());
            dto.setNome(cliente.getNome());
            dto.setTelefone(cliente.getTelefone());
            dto.setImgPerfil(cliente.getImgPerfil());

            // transforma agendamentos em resumos
            if (cliente.getAgendamentos() != null) {
                dto.setAgendamentos(cliente.getAgendamentos().stream().map(ag -> {
                    AgendamentoResumoDTO resumo = new AgendamentoResumoDTO();
                    resumo.setIdAgendamento(ag.getIdAgendamento());
                    resumo.setTitulo(ag.getTitulo());
                    resumo.setDescricao(ag.getDescricao());
                    resumo.setDataInicio(ag.getDataInicio());
                    resumo.setDataFim(ag.getDataFim());
                    resumo.setNomeCliente(ag.getCliente() != null ? ag.getCliente().getNome() : null);
                    resumo.setNomeEmpresa(ag.getEmpresa() != null ? ag.getEmpresa().getNomeFantasia() : null);
                    return resumo;
                }).collect(Collectors.toList()));
            }

            return dto;
        }).collect(Collectors.toList());
    }


    //consultar cliente pelo nome
    public List<Cliente> consultarClientePorNome(String nome) {
        return clienteRepository.findByNomeIgnoreCaseContaining(nome);
    }

    //consultar cliente especifico
    public Cliente consultarClientePorId(int idCliente) {
        Cliente clienteEspecifico = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado!!"));

        return clienteEspecifico;
    }

    //deletar cliente pelo id
    public void delete(int idCliente){
        clienteRepository.deleteById(idCliente);           //metodo que faz o delete do usuario

    }

}
