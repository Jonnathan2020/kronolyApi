package com.kronoly.Service;

import com.kronoly.DTO.*;
import com.kronoly.Entity.*;
import com.kronoly.Entity.Enuns.StatusAgendamento;
import com.kronoly.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private AgendamentoProdutoRepository agendamentoProdutoRepository;
    @Autowired
    private AgendamentoServicoRepository agendamentoServicoRepository;
    @Autowired
    private HorarioService horarioService;

    private LocalDateTime calcularDataFim(
            LocalDateTime dataInicio,
            List<AgendamentoServico> servicos
    ) {
        if (dataInicio == null || servicos == null || servicos.isEmpty()) {
            return dataInicio;
        }

        long minutosTotais = servicos.stream()
                .mapToLong(s ->
                        s.getServico().getTempoEstimado()
                                * s.getQuantServicos()
                )
                .sum();

        return dataInicio.plusMinutes(minutosTotais);
    }

    /* ============================================================
       CADASTRAR AGENDAMENTO
       ============================================================ */
    public AgendamentoDTO cadastrarAgendamento(AgendamentoCreateDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseGet(() -> clienteRepository.findById(1003)
                        .orElseThrow(() -> new RuntimeException("Empresa padrão não encontrada no sistema")));

        Empresa empresa = empresaRepository.findById(dto.getIdEmpresa())
                .orElseGet(() -> empresaRepository.findById(2)
                        .orElseThrow(() -> new RuntimeException("Empresa padrão não encontrada no sistema")));

        // 🔹 CRIA AGENDAMENTO
        Agendamento agendamento = new Agendamento();
        agendamento.setTitulo(dto.getTitulo());
        agendamento.setDescricao(dto.getDescricao());

        LocalDateTime dataInicio = LocalDateTime.of(dto.getData(), dto.getHora());
        agendamento.setDataInicio(dataInicio);
        agendamento.setNomeCliente(dto.getNomeCliente());
        agendamento.setContatoCliente(dto.getContatoCliente());
        agendamento.setCliente(cliente);
        agendamento.setEmpresa(empresa);
        agendamento.setFormaPagtoEnum(dto.getFormaPagtoEnum());
        agendamento.setStatusAgendamento(StatusAgendamento.PENDENTE);

    /* ============================================================
       PRODUTOS
       ============================================================ */
        double totalProdutos = 0;

        if (dto.getProdutos() != null) {
            for (int idProduto : dto.getProdutos()) {

                Produto produto = produtoRepository.findById(idProduto)
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                AgendamentoProduto ap = new AgendamentoProduto();
                ap.setAgendamento(agendamento);
                ap.setProduto(produto);
                ap.setQuantProdutos(1);
                ap.setValorCusto(produto.getValorCusto());
                ap.setValorVenda(produto.getValorVenda());

                agendamento.getProdutos().add(ap);
                totalProdutos += ap.getValorVenda();
            }
        }

    /* ============================================================
       SERVIÇOS
       ============================================================ */
        double totalServicos = 0;

        if (dto.getServicos() == null || dto.getServicos().isEmpty()) {
            throw new RuntimeException("Agendamento precisa ter ao menos um serviço");
        }

        for (int idServico : dto.getServicos()) {

            Servico servico = servicoRepository.findById(idServico)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

            AgendamentoServico as = new AgendamentoServico();
            as.setAgendamento(agendamento);
            as.setServico(servico);
            as.setQuantServicos(1);
            as.setValorCusto(servico.getValorCusto());
            as.setValorServico(servico.getValorServico());

            agendamento.getServicos().add(as);
            totalServicos += as.getValorServico();
        }

    /* ============================================================
       FINALIZA
       ============================================================ */
        agendamento.setValorProdutos(totalProdutos);
        agendamento.setValorServicos(totalServicos);

        agendamento.setDataFim(
                calcularDataFim(
                        dataInicio,
                        agendamento.getServicos()
                )
        );

        //Tornar horário selecionado indisponivel
        horarioService.bloquearHorariosDoAgendamento(agendamento.getDataInicio(), agendamento.getDataFim());
        //horarioService.alterarDisponibilidadePorDataEHora(dto.getData(), dto.getHora());


        // 🔥 UM ÚNICO SAVE
        return new AgendamentoDTO(
                agendamentoRepository.save(agendamento)
        );
    }


    @Transactional(readOnly = true)
    public List<AgendamentoDTO> consultarAgendamentos() {

        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        if (agendamentos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum agendamento encontrado!");
        }

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

                ag.getCliente() != null ? new ClienteResumoDTO(
                        ag.getCliente().getIdCliente(),
                        ag.getCliente().getDocumento(),
                        ag.getCliente().getNome(),
                        ag.getCliente().getTelefone()
                ) : null,

                ag.getEmpresa() != null ? new EmpresaResumoDTO(
                        ag.getEmpresa().getIdEmpresa(),
                        ag.getEmpresa().getDocumento(),
                        ag.getEmpresa().getRazaoSocial(),
                        ag.getEmpresa().getNomeFantasia(),
                        ag.getEmpresa().getRepresentante(),
                        ag.getEmpresa().getLogo(),
                        ag.getEmpresa().getStatusEmpresaEnum()
                ) : null,

                ag.getProdutos().stream()
                        .map(ProdutoResumoDTO::new)
                        .collect(Collectors.toList()),

                ag.getServicos().stream()
                        .map(ServicoResumoDTO::new)
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    /* ============================================================
       CONSULTAR POR ID
       ============================================================ */
    @Transactional(readOnly = true)
    public AgendamentoDTO consultarAgendamentoPorId(int idAgendamento) {

        Agendamento ag = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado!!"));

        return new AgendamentoDTO(
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

                ag.getCliente() != null ? new ClienteResumoDTO(
                        ag.getCliente().getIdCliente(),
                        ag.getCliente().getDocumento(),
                        ag.getCliente().getNome(),
                        ag.getCliente().getTelefone()
                ) : null,

                ag.getEmpresa() != null ? new EmpresaResumoDTO(
                        ag.getEmpresa().getIdEmpresa(),
                        ag.getEmpresa().getDocumento(),
                        ag.getEmpresa().getRazaoSocial(),
                        ag.getEmpresa().getNomeFantasia(),
                        ag.getEmpresa().getRepresentante(),
                        ag.getEmpresa().getLogo(),
                        ag.getEmpresa().getStatusEmpresaEnum()
                ) : null,

                ag.getProdutos().stream()
                        .map(ProdutoResumoDTO::new)
                        .collect(Collectors.toList()),

                ag.getServicos().stream()
                        .map(ServicoResumoDTO::new)
                        .collect(Collectors.toList())
        );
    }

    /* ============================================================
       CONSULTAR POR DATA
       ============================================================ */
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> consultarAgendamentoPorData(LocalDate data) {

        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(23, 59, 59);

        return agendamentoRepository.findByDataInicioBetween(inicio, fim)
                .stream()
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

                        ag.getCliente() != null ? new ClienteResumoDTO(
                                ag.getCliente().getIdCliente(),
                                ag.getCliente().getDocumento(),
                                ag.getCliente().getNome(),
                                ag.getCliente().getTelefone()
                        ) : null,

                        ag.getEmpresa() != null ? new EmpresaResumoDTO(
                                ag.getEmpresa().getIdEmpresa(),
                                ag.getEmpresa().getDocumento(),
                                ag.getEmpresa().getRazaoSocial(),
                                ag.getEmpresa().getNomeFantasia(),
                                ag.getEmpresa().getRepresentante(),
                                ag.getEmpresa().getLogo(),
                                ag.getEmpresa().getStatusEmpresaEnum()
                        ) : null,

                        ag.getProdutos().stream().map(ProdutoResumoDTO::new).toList(),
                        ag.getServicos().stream().map(ServicoResumoDTO::new).toList()
                )).toList();
    }

    /* ============================================================
       CONSULTAR POR EMPRESA
       ============================================================ */
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> consultarAgendamentosPorEmpresa(int idEmpresa) {

        empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        return agendamentoRepository.findByEmpresa_idEmpresa(idEmpresa)
                .stream()
                .map(this::consultarAgendamentoPorIdInterno)
                .toList();
    }

    /* ============================================================
       CONSULTAR POR CLIENTE
       ============================================================ */
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> consultarAgendamentosPorCliente(int idCliente) {

        clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        return agendamentoRepository.findByCliente_idCliente(idCliente)
                .stream()
                .map(this::consultarAgendamentoPorIdInterno)
                .toList();
    }

    /* ============================================================
       MÉTODO AUXILIAR INTERNO (SEM SAVE)
       ============================================================ */
    private AgendamentoDTO consultarAgendamentoPorIdInterno(Agendamento ag) {

        return new AgendamentoDTO(
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

                ag.getCliente() != null ? new ClienteResumoDTO(
                        ag.getCliente().getIdCliente(),
                        ag.getCliente().getDocumento(),
                        ag.getCliente().getNome(),
                        ag.getCliente().getTelefone()
                ) : null,

                ag.getEmpresa() != null ? new EmpresaResumoDTO(
                        ag.getEmpresa().getIdEmpresa(),
                        ag.getEmpresa().getDocumento(),
                        ag.getEmpresa().getRazaoSocial(),
                        ag.getEmpresa().getNomeFantasia(),
                        ag.getEmpresa().getRepresentante(),
                        ag.getEmpresa().getLogo(),
                        ag.getEmpresa().getStatusEmpresaEnum()
                ) : null,

                ag.getProdutos().stream().map(ProdutoResumoDTO::new).toList(),
                ag.getServicos().stream().map(ServicoResumoDTO::new).toList()
        );
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
        if (agendamentoUpdateDTO.getProdutos() != null) {

            // 1️⃣ Remove vínculos antigos
            agendamentoProdutoRepository.deleteByAgendamento(agendamentoExistente);

            BigDecimal totalProdutos = BigDecimal.ZERO;

            // 2️⃣ Cria novos vínculos
            for (ProdutoUpdateDTO dto : agendamentoUpdateDTO.getProdutos()) {

                Produto produto = produtoRepository.findById(dto.getIdProduto())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                AgendamentoProduto ap = new AgendamentoProduto();
                ap.setAgendamento(agendamentoExistente);
                ap.setProduto(produto);
                ap.setQuantProdutos(dto.getQuantProduto());
                ap.setValorVenda(dto.getValorVenda());

                agendamentoProdutoRepository.save(ap);
            }

            agendamentoExistente.setValorProdutos(agendamentoExistente.valorProdutos);
        }

        if (agendamentoUpdateDTO.getServicos() != null) {

            // 1️⃣ Remove vínculos antigos
            agendamentoServicoRepository.deleteByAgendamento(agendamentoExistente);

            BigDecimal totalProdutos = BigDecimal.ZERO;

            // 2️⃣ Cria novos vínculos
            for (ServicoUpdateDTO servicoUpdateDTO : agendamentoUpdateDTO.getServicos()) {

                Servico servico = servicoRepository.findById(servicoUpdateDTO.getIdServico())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

                AgendamentoServico as = new AgendamentoServico();
                as.setAgendamento(agendamentoExistente);
                as.setServico(servico);

                as.setQuantServicos(servicoUpdateDTO.getQuantServicos());
                as.setTempoEstimado(servicoUpdateDTO.getTempoEstimado());
                as.setValorCusto(servicoUpdateDTO.getValorCusto());
                as.setValorServico(servicoUpdateDTO.getValorServico());

                agendamentoServicoRepository.save(as);
            }

            agendamentoExistente.setValorProdutos(agendamentoExistente.valorProdutos);
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

