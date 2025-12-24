package com.kronoly.Service;

import com.kronoly.DTO.*;
import com.kronoly.Entity.*;
import com.kronoly.Entity.Enuns.StatusAgendamento;
import com.kronoly.Repository.AgendamentoRepository;
import com.kronoly.Repository.ClienteRepository;
import com.kronoly.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private LocalDateTime calcularDataFim(
            LocalDateTime dataInicio,
            List<Servico> servicos
    ) {
        if (dataInicio == null || servicos == null || servicos.isEmpty()) {
            return dataInicio;
        }

        long minutosTotais = servicos.stream()
                .filter(s -> s.getTempoEstimado() != null)
                .mapToLong(Servico::getTempoEstimado)
                .sum();

        return dataInicio.plusMinutes(minutosTotais);
    }

    public AgendamentoDTO cadastrarAgendamento(AgendamentoCreateDTO agendamentoCreateDTO){

        Cliente cliente = clienteRepository.findById(agendamentoCreateDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Empresa empresa = empresaRepository.findById(agendamentoCreateDTO.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Agendamento agendamento = new Agendamento();
        agendamento.setTitulo(agendamentoCreateDTO.getTitulo());
        agendamento.setDescricao(agendamentoCreateDTO.getDescricao());
        agendamento.setProdutos(agendamentoCreateDTO.getProdutos());
        agendamento.setServicos(agendamentoCreateDTO.getServicos());
        LocalDateTime dataHora = LocalDateTime.of(
                agendamentoCreateDTO.getData(),
                agendamentoCreateDTO.getHora()
        );
        agendamento.setDataInicio(dataHora);
        // 🔥 calcula automaticamente
        LocalDateTime dataFim = calcularDataFim(
                dataHora,
                agendamentoCreateDTO.getServicos()
        );
        agendamento.setDataFim(dataFim);
        agendamento.setNomeCliente(agendamentoCreateDTO.getNomeCliente());
        agendamento.setContatoCliente(agendamentoCreateDTO.getContatoCliente());
        agendamento.setCliente(cliente);
        agendamento.setEmpresa(empresa);
        agendamento.setValorServicos(agendamentoCreateDTO.getValorServicos());
        agendamento.setValorProdutos(agendamentoCreateDTO.getValorProdutos());
        agendamento.setFormaPagtoEnum(agendamentoCreateDTO.getFormaPagtoEnum());
        agendamento.setStatusAgendamento(StatusAgendamento.PENDENTE);

        return new AgendamentoDTO(agendamentoRepository.save(agendamento));
    }

    public List<AgendamentoDTO> consultarAgendamentos() {

        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        if (agendamentos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum agendamento encontrado!");
        }

        // Converte cada Agendamento em AgendamentoDTO
        return agendamentos.stream().map(agendamento -> {

            // Cria DTOs dos produtos
            List<ProdutoResumoDTO> produtosDTO = agendamento.getProdutos().stream()
                    .map(ProdutoResumoDTO::new)
                    .collect(Collectors.toList());

            // Cria DTOs dos serviços
            List<ServicoResumoDTO> servicosDTO = agendamento.getServicos().stream()
                    .map(ServicoResumoDTO::new)
                    .collect(Collectors.toList());

            ClienteResumoDTO clienteDTO = null;

            if (agendamento.getCliente() != null) {
                clienteDTO = new ClienteResumoDTO(
                        agendamento.getCliente().getIdCliente(),
                        agendamento.getCliente().getDocumento(),
                        agendamento.getCliente().getNome(),
                        agendamento.getCliente().getTelefone()
                );
            }

            EmpresaResumoDTO empresaDTO = null;

            if (agendamento.getEmpresa() != null) {
                empresaDTO = new EmpresaResumoDTO(
                        agendamento.getEmpresa().getIdEmpresa(),
                        agendamento.getEmpresa().getDocumento(),
                        agendamento.getEmpresa().getRazaoSocial(),
                        agendamento.getEmpresa().getNomeFantasia(),
                        agendamento.getEmpresa().getRepresentante(),
                        agendamento.getEmpresa().getLogo(),
                        agendamento.getEmpresa().getStatusEmpresaEnum()
                );
            }
            // Retorna o AgendamentoDTO pronto
            return new AgendamentoDTO(
                    agendamento.getIdAgendamento(),
                    agendamento.getTitulo(),
                    agendamento.getDescricao(),
                    agendamento.getDataInicio(),
                    agendamento.getDataFim(),
                    agendamento.getNomeCliente(),
                    agendamento.getContatoCliente(),
                    agendamento.getValorServicos(),
                    agendamento.getValorProdutos(),
                    agendamento.getFormaPagtoEnum(),
                    agendamento.getStatusAgendamento(),
                    clienteDTO,
                    empresaDTO,
                    produtosDTO,
                    servicosDTO
            );

        }).collect(Collectors.toList());
    }

    public AgendamentoDTO consultarAgendamentoPorId(int idAgendamento){
        Agendamento agendamentoEspecifico = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrada!!"));

        return new AgendamentoDTO(agendamentoRepository.save(agendamentoEspecifico));
    }

    public List<AgendamentoDTO> consultarAgendamentoPorData(LocalDate data) {

        LocalDateTime inicioDoDia = data.atStartOfDay();
        LocalDateTime fimDoDia = data.atTime(23, 59, 59);

        List<Agendamento> agendamentos =
                agendamentoRepository.findByDataInicioBetween(
                        inicioDoDia,
                        fimDoDia
                );

        return agendamentos.stream()
                .map(ag -> new AgendamentoDTO(
                        ag.getIdAgendamento(),
                        ag.getTitulo(),
                        ag.getDescricao(),
                        ag.getDataInicio(),
                        ag.getDataFim(),
                        ag.getNomeCliente(),
                        ag.getContatoCliente(),
                        ag.getValorServicos(),
                        ag.getValorProdutos(),
                        ag.getFormaPagtoEnum(),
                        ag.getStatusAgendamento(),

                        ag.getCliente() != null
                                ? new ClienteResumoDTO(
                                ag.getCliente().getIdCliente(),
                                ag.getCliente().getDocumento(),
                                ag.getCliente().getNome(),
                                ag.getCliente().getTelefone()
                        )
                                : null,

                        ag.getEmpresa() != null
                                ? new EmpresaResumoDTO(
                                ag.getEmpresa().getIdEmpresa(),
                                ag.getEmpresa().getDocumento(),
                                ag.getEmpresa().getRazaoSocial(),
                                ag.getEmpresa().getNomeFantasia(),
                                ag.getEmpresa().getRepresentante(),
                                ag.getEmpresa().getLogo(),
                                ag.getEmpresa().getStatusEmpresaEnum()
                        )
                                : null,

                        ag.getProdutos() != null
                                ? ag.getProdutos().stream()
                                .map(ProdutoResumoDTO::new)
                                .toList()
                                : null,

                        ag.getServicos() != null
                                ? ag.getServicos().stream()
                                .map(ServicoResumoDTO::new)
                                .toList()
                                : null
                ))
                .toList();
    }


    public List<AgendamentoDTO> consultarAgendamentosPorEmpresa(int idEmpresa) {
        // 1️⃣ Verifica se a empresa existe
        empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        // 2️⃣ Busca todas as agendas da empresa
        List<Agendamento> agendamentos = agendamentoRepository.findByEmpresa_idEmpresa(idEmpresa);

        if (agendamentos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum agendamento encontrado para essa empresa");
        }

        // 3️⃣ Mapeia para DTOs
        return agendamentos.stream().map(ag-> new AgendamentoDTO(
                ag.getIdAgendamento(),
                ag.getTitulo(),
                ag.getDescricao(),
                ag.getDataInicio(),
                ag.getDataFim(),
                ag.getNomeCliente(),
                ag.getContatoCliente(),
                ag.getValorServicos(),
                ag.getValorProdutos(),
                ag.getFormaPagtoEnum(),
                ag.getStatusAgendamento(),
                // Conversão do Cliente
                ag.getCliente() != null ? new ClienteResumoDTO(
                        ag.getCliente().getIdCliente(),
                        ag.getCliente().getDocumento(),
                        ag.getCliente().getNome(),
                        ag.getCliente().getTelefone()
                ) : null,

                // Conversão da Empresa
                ag.getEmpresa() != null ? new EmpresaResumoDTO(
                        ag.getEmpresa().getIdEmpresa(),
                        ag.getEmpresa().getDocumento(),
                        ag.getEmpresa().getRazaoSocial(),
                        ag.getEmpresa().getNomeFantasia(),
                        ag.getEmpresa().getRepresentante(),
                        ag.getEmpresa().getLogo(),
                        ag.getEmpresa().getStatusEmpresaEnum()
                ) : null,

                // Conversão da lista de Produtos
                ag.getProdutos() != null ? ag.getProdutos().stream()
                        .map(ProdutoResumoDTO::new)
                        .collect(Collectors.toList()) : null,

                // Conversão da lista de Serviços
                ag.getServicos() != null ? ag.getServicos().stream()
                        .map(ServicoResumoDTO::new)
                        .collect(Collectors.toList()) : null
        )).toList();
    }

    public List<AgendamentoDTO> consultarAgendamentosPorCliente(int idCliente) {
        // Primeiro, verifica se a empresa existe
        clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrada"));

        // Busca todas as agendas dessa empresa
        List<Agendamento> agendamentos = agendamentoRepository.findByCliente_idCliente(idCliente);

        if (agendamentos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum agendamento encontrado para essa empresa");
        }

        // 3️⃣ Mapeia para DTOs
        return agendamentos.stream().map(ag -> new AgendamentoDTO(
                ag.getIdAgendamento(),
                ag.getTitulo(),
                ag.getDescricao(),
                ag.getDataInicio(),
                ag.getDataFim(),
                ag.getNomeCliente(),
                ag.getContatoCliente(),
                ag.getValorServicos(),
                ag.getValorProdutos(),
                ag.getFormaPagtoEnum(),
                ag.getStatusAgendamento(),
                // Conversão do Cliente
                ag.getCliente() != null ? new ClienteResumoDTO(
                        ag.getCliente().getIdCliente(),
                        ag.getCliente().getDocumento(),
                        ag.getCliente().getNome(),
                        ag.getCliente().getTelefone()
                ) : null,

                // Conversão da Empresa
                ag.getEmpresa() != null ? new EmpresaResumoDTO(
                        ag.getEmpresa().getIdEmpresa(),
                        ag.getEmpresa().getDocumento(),
                        ag.getEmpresa().getRazaoSocial(),
                        ag.getEmpresa().getNomeFantasia(),
                        ag.getEmpresa().getRepresentante(),
                        ag.getEmpresa().getLogo(),
                        ag.getEmpresa().getStatusEmpresaEnum()
                ) : null,

                // Conversão da lista de Produtos
                ag.getProdutos() != null ? ag.getProdutos().stream()
                        .map(ProdutoResumoDTO::new)
                        .collect(Collectors.toList()) : null,

                // Conversão da lista de Serviços
                ag.getServicos() != null ? ag.getServicos().stream()
                        .map(ServicoResumoDTO::new)
                        .collect(Collectors.toList()) : null
        )).toList();
    }

    public Agendamento alterarAgendamento(int idAgendamento, AgendamentoUpdateDTO agendamentoUpdateDTO){
        Agendamento agendamentoExistente = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado!!!"));

        if(agendamentoUpdateDTO.getTitulo() != null){
            agendamentoExistente.setTitulo(agendamentoUpdateDTO.getTitulo());
        }
        if(agendamentoUpdateDTO.getDescricao() != null){
            agendamentoExistente.setDescricao(agendamentoUpdateDTO.getDescricao());
        }
        if(agendamentoUpdateDTO.getNomeCliente() != null){
            agendamentoExistente.setNomeCliente(agendamentoUpdateDTO.getNomeCliente());
        }
        if(agendamentoUpdateDTO.getContatoCliente() != 0){
            agendamentoExistente.setContatoCliente(agendamentoUpdateDTO.getContatoCliente());
        }
        if(agendamentoUpdateDTO.getDataInicio() != null){
            agendamentoExistente.setDataInicio(agendamentoUpdateDTO.getDataInicio());
        }
        if(agendamentoUpdateDTO.getDataFim() != null){
            agendamentoExistente.setDataFim(agendamentoUpdateDTO.getDataFim());
        }
        if(agendamentoUpdateDTO.getFormaPagtoEnum() != null){
            agendamentoExistente.setFormaPagtoEnum(agendamentoUpdateDTO.getFormaPagtoEnum());
        }
        if(agendamentoUpdateDTO.getProdutos() != null){
            agendamentoExistente.setProdutos(agendamentoUpdateDTO.getProdutos());
        }
        if (agendamentoUpdateDTO.getServicos() != null){
            agendamentoExistente.setServicos(agendamentoUpdateDTO.getServicos());
        }
        if(agendamentoUpdateDTO.getValorProdutos() != -1000){
            agendamentoExistente.setValorProdutos(agendamentoUpdateDTO.getValorProdutos());
        }
        if(agendamentoUpdateDTO.getValorServicos() != -1000){
            agendamentoExistente.setValorServicos(agendamentoUpdateDTO.getValorServicos());
        }
        if (agendamentoUpdateDTO.getDataInicio() != null ||
                agendamentoUpdateDTO.getServicos() != null) {

            agendamentoExistente.setDataFim(
                    calcularDataFim(
                            agendamentoExistente.getDataInicio(),
                            agendamentoExistente.getServicos()
                    )
            );
        }


        return agendamentoRepository.save(agendamentoExistente);
    }

    public void delete(int idAgendamento){
        agendamentoRepository.deleteById(idAgendamento);
    }

    public AgendamentoDTO confirmarAgendamento (int idAgendamento){
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!!"));

        if(!agendamento.getStatusAgendamento().equals(StatusAgendamento.PENDENTE)){
            throw new IllegalArgumentException("Somente agendamentos pendentes podem ser confirmados!!!");
        }

        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.CONFIRMADO)){
            throw new IllegalArgumentException("Agendamento já confirmado!!");
        }

        agendamento.setStatusAgendamento(StatusAgendamento.CONFIRMADO);

        return new AgendamentoDTO(agendamento);
    }

    public AgendamentoDTO iniciarAgendamento (int idAgendamento){
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!!"));

        if(!agendamento.getStatusAgendamento().equals(StatusAgendamento.CONFIRMADO)){
            throw new IllegalArgumentException("Somente agendamentos confirmados podem ser iniciados!!!");
        }

        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.EM_ANDAMENTO)){
            throw new IllegalArgumentException("Agendamento já iniciado!!");
        }

        agendamento.setStatusAgendamento(StatusAgendamento.EM_ANDAMENTO);

        return new AgendamentoDTO(agendamento);
    }

    public AgendamentoDTO finalizarAgendamento (int idAgendamento){
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!!"));

        if(!agendamento.getStatusAgendamento().equals(StatusAgendamento.EM_ANDAMENTO)){
            throw new IllegalArgumentException("Somente agendamentos pendentes podem ser confirmados!!!");
        }

        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.CONCLUIDO)){
            throw new IllegalArgumentException("Agendamento já finalizado!!");
        }

        agendamento.setStatusAgendamento(StatusAgendamento.CONCLUIDO);

        return new AgendamentoDTO(agendamento);
    }

    public AgendamentoDTO cancelarAgendamentoPrestador (int idAgendamento){
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!!"));


        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.CANCELADO_PRESTADOR)){
            throw new IllegalArgumentException("Agendamento já cancelado!!");
        }

        agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO_PRESTADOR);

        return new AgendamentoDTO(agendamento);
    }

    public AgendamentoDTO cancelarAgendamentoCliente (int idAgendamento){
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!!"));


        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.CANCELADO_CLIENTE)){
            throw new IllegalArgumentException("Agendamento já cancelado!!");
        }

        agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO_CLIENTE);

        return new AgendamentoDTO(agendamento);
    }

    public AgendamentoDTO reportarClienteAusente (int idAgendamento){
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado!!"));

        if(!agendamento.getStatusAgendamento().equals(StatusAgendamento.CONFIRMADO)){
            throw new IllegalArgumentException("Somente agendamentos confirmados podem ser reportados!!!");
        }

        if (agendamento.getStatusAgendamento().equals(StatusAgendamento.CLIENTE_AUSENTE)){
            throw new IllegalArgumentException("Agendamento já reportado!!");
        }

        agendamento.setStatusAgendamento(StatusAgendamento.CLIENTE_AUSENTE);

        return new AgendamentoDTO(agendamento);
    }

}

