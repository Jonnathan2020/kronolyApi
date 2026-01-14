package com.kronoly.Service;

import com.kronoly.DTO.AgendaCreateDTO;
import com.kronoly.DTO.AgendaResponseDTO;
import com.kronoly.DTO.AgendaUpdateDTO;
import com.kronoly.DTO.EmpresaResumoDTO;
import com.kronoly.Entity.Agenda;
import com.kronoly.Entity.Cliente;
import com.kronoly.Entity.Empresa;
import com.kronoly.Entity.Enuns.StatusAgendaEnum;
import com.kronoly.Repository.AgendaRepository;
import com.kronoly.Repository.EmpresaRepository;
import com.kronoly.Repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private HorarioService horarioService;
    @Autowired
    private HorarioRepository horarioRepository;

    private AgendaResponseDTO converterParaResponseDTO(Agenda agenda) {
        AgendaResponseDTO response = new AgendaResponseDTO();
        response.setIdAgenda(agenda.getIdAgenda());
        response.setDataInicio(agenda.getDataInicio());
        response.setDataFinal(agenda.getDataFinal());
        response.setHoraAbertura(agenda.getHoraAbertura());
        response.setHoraAlmoco(agenda.getHoraAlmoco());
        response.setHoraRetornoAlmoco(agenda.getHoraRetornoAlmoco());
        response.setHoraFechamento(agenda.getHoraFechamento());
        response.setDuracaoSlot(agenda.getDuracaoSlot());
        response.setDiasSemana(agenda.getDiasSemana());
        response.setCriadoEm(agenda.getCriadoEm());
        response.setAtualizadoEm(agenda.getAtualizadoEm());

        // Mapeia o Resumo da Empresa
        if (agenda.getEmpresa() != null) {
            EmpresaResumoDTO resumo = new EmpresaResumoDTO();
            resumo.setIdEmpresa(agenda.getEmpresa().getIdEmpresa());
            resumo.setDocumento(agenda.getEmpresa().getDocumento());
            resumo.setRazaoSocial(agenda.getEmpresa().getRazaoSocial());
            resumo.setNomeFantasia(agenda.getEmpresa().getNomeFantasia());
            resumo.setRepresentante(agenda.getEmpresa().getRepresentante());
            resumo.setLogo(agenda.getEmpresa().getLogo());
            response.setEmpresa(resumo);
        }

        return response;
    }

    public AgendaResponseDTO cadastrarAgenda(AgendaCreateDTO agendaCreateDTO) {

        Empresa empresa = empresaRepository.findById(agendaCreateDTO.getIdEmpresa())
                .orElseGet(() -> empresaRepository.findById(2)
                        .orElseThrow(() -> new RuntimeException("Empresa padrão não encontrada no sistema")));

        Agenda agenda = new Agenda();

        agenda.setStatusAgendaEnum(StatusAgendaEnum.ABERTA);
        agenda.setDataInicio(agendaCreateDTO.getDataInicio());
        agenda.setDataFinal(agendaCreateDTO.getDataFinal());
        agenda.setHoraAbertura(agendaCreateDTO.getHoraAbertura());
        agenda.setHoraAlmoco(agendaCreateDTO.getHoraAlmoco());
        agenda.setHoraRetornoAlmoco(agendaCreateDTO.getHoraRetornoAlmoco());
        agenda.setHoraFechamento(agendaCreateDTO.getHoraFechamento());
        agenda.setEmpresa(empresa);
        agenda.setCriadoEm(LocalDateTime.now());
        agenda.setAtualizadoEm(LocalDateTime.now());
        agenda.setDuracaoSlot(agendaCreateDTO.duracaoSlot);
        agenda.setDiasSemana(agendaCreateDTO.getDiasSemana());
        agenda = agendaRepository.save(agenda);

        horarioService.cadastrarHorarios(agenda, agendaCreateDTO.getIdEmpresa());


        return converterParaResponseDTO(agenda);
    }

    public List<AgendaResponseDTO> consultarAgendas(){
          List<Agenda> agendas = agendaRepository.findAll();
        // Transforma cada Agenda da lista em um AgendaResponseDTO
        return agendas.stream()
                .map(this::converterParaResponseDTO) // Reaproveita o metodo que já criamos
                .toList(); // No Java 16+ ou 17 (padrão em 2026), usa-se .toList()
    }


    public AgendaResponseDTO consultarAgendaPorId(int idAgenda){
        Agenda agendaEspecifica = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada!!"));

        return converterParaResponseDTO(agendaEspecifica);
    }

    public List<AgendaResponseDTO> consultarAgendasPorEmpresa(int idEmpresa) {
        // Primeiro, verifica se a empresa existe
        empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        // Busca todas as agendas dessa empresa
        List<Agenda> agendas = agendaRepository.findByEmpresa_idEmpresa(idEmpresa);

        if (agendas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma agenda encontrada para essa empresa");
        }

        // Converte a lista de entidades para lista de DTOs
        return agendas.stream()
                .map(this::converterParaResponseDTO)
                .toList();
    }

    public AgendaResponseDTO alterarAgenda(int idAgenda, AgendaUpdateDTO agendaUpdateDTO){
        Agenda agendaExistente = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada!!!"));

        if(agendaUpdateDTO.getStatusAgendaEnum() != null){
            agendaExistente.setStatusAgendaEnum(agendaUpdateDTO.getStatusAgendaEnum());
        }

        if(agendaUpdateDTO.getDataInicio() != null){
            agendaExistente.setDataInicio(agendaUpdateDTO.getDataInicio());
        }

        if (agendaUpdateDTO.getDataFinal() != null){
            agendaExistente.setDataFinal(agendaUpdateDTO.getDataFinal());
        }

        if (agendaUpdateDTO.getHoraAbertura() != null){
            agendaExistente.setHoraAbertura(agendaUpdateDTO.getHoraAbertura());
        }

        if(agendaUpdateDTO.getHoraAlmoco() != null){
            agendaExistente.setHoraAlmoco(agendaUpdateDTO.getHoraAlmoco());
        }

        if(agendaUpdateDTO.getHoraFechamento() != null){
            agendaUpdateDTO.setHoraFechamento(agendaUpdateDTO.getHoraFechamento());
        }
        // Atualiza o timestamp de modificação
        agendaExistente.setAtualizadoEm(LocalDateTime.now());

        Agenda agendaSalva = agendaRepository.save(agendaExistente);

        return converterParaResponseDTO(agendaSalva);

    }

    public void delete(int idAgenda){
        agendaRepository.deleteById(idAgenda);
    }
}
