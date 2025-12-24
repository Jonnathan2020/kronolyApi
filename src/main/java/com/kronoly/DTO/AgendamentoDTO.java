package com.kronoly.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kronoly.Entity.Agendamento;
import com.kronoly.Entity.Enuns.FormaPagtoEnum;
import com.kronoly.Entity.Enuns.StatusAgendamento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AgendamentoDTO {

    private int idAgendamento;
    private String titulo;
    private String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataFim;

    private String nomeCliente;
    private int contatoCliente;
    private double valorServicos;
    private double valorProdutos;
    private FormaPagtoEnum formaPagtoEnum;
    private StatusAgendamento statusAgendamento;

    private ClienteResumoDTO cliente;
    private EmpresaResumoDTO empresa;
    private List<ProdutoResumoDTO> produtos;
    private List<ServicoResumoDTO> servicos;

    // ✅ Construtor completo
    public AgendamentoDTO(
            int idAgendamento,
            String titulo,
            String descricao,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            String nomeCliente,
            int contatoCliente,
            double valorServicos,
            double valorProdutos,
            FormaPagtoEnum formaPagtoEnum,
            StatusAgendamento statusAgendamento,
            ClienteResumoDTO cliente,
            EmpresaResumoDTO empresa,
            List<ProdutoResumoDTO> produtos,
            List<ServicoResumoDTO> servicos
    ) {
        this.idAgendamento = idAgendamento;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.nomeCliente = nomeCliente;
        this.contatoCliente = contatoCliente;
        this.valorServicos = valorServicos;
        this.valorProdutos = valorProdutos;
        this.formaPagtoEnum = formaPagtoEnum;
        this.statusAgendamento = statusAgendamento;
        this.cliente = cliente;
        this.empresa = empresa;
        this.produtos = produtos;
        this.servicos = servicos;
    }

    // ✅ Construtor de conversão direta da entidade Agendamento
    public AgendamentoDTO(Agendamento agendamento) {
        this.idAgendamento = agendamento.getIdAgendamento();
        this.titulo = agendamento.getTitulo();
        this.descricao = agendamento.getDescricao();
        this.dataInicio = agendamento.getDataInicio();
        this.dataFim = agendamento.getDataFim();
        this.nomeCliente = agendamento.getNomeCliente();
        this.valorServicos = agendamento.getValorServicos();
        this.valorProdutos = agendamento.getValorProdutos();
        this.formaPagtoEnum = agendamento.getFormaPagtoEnum();
        this.statusAgendamento = agendamento.getStatusAgendamento();

        if (agendamento.getCliente() != null) {
            this.cliente = new ClienteResumoDTO(
                    agendamento.getCliente().getIdCliente(),
                    agendamento.getCliente().getDocumento(),
                    agendamento.getCliente().getNome(),
                    agendamento.getCliente().getTelefone()
            );
        }

        if (agendamento.getEmpresa() != null) {
            this.empresa = new EmpresaResumoDTO(
                    agendamento.getEmpresa().getIdEmpresa(),
                    agendamento.getEmpresa().getDocumento(),
                    agendamento.getEmpresa().getRazaoSocial(),
                    agendamento.getEmpresa().getNomeFantasia(),
                    agendamento.getEmpresa().getRepresentante(),
                    agendamento.getEmpresa().getLogo(),
                    agendamento.getEmpresa().getStatusEmpresaEnum()
            );
        }

        if (agendamento.getProdutos() != null) {
            this.produtos = agendamento.getProdutos()
                    .stream()
                    .map(ProdutoResumoDTO::new)
                    .collect(Collectors.toList());
        }

        if (agendamento.getServicos() != null) {
            this.servicos = agendamento.getServicos()
                    .stream()
                    .map(ServicoResumoDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
