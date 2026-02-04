package com.kronoly.Service;

import com.kronoly.DTO.AgendaCreateDTO;
import com.kronoly.DTO.AgendaResponseDTO;
import com.kronoly.DTO.EmpresaResumoDTO;
import com.kronoly.DTO.HorarioResponseDTO;
import com.kronoly.Entity.Agenda;
import com.kronoly.Entity.Cliente;
import com.kronoly.Entity.Empresa;
import com.kronoly.Entity.Horario;
import com.kronoly.Repository.AgendaRepository;
import com.kronoly.Repository.EmpresaRepository;
import com.kronoly.Repository.HorarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    private HorarioResponseDTO converterParaResponseDTO(Horario horario) {
        HorarioResponseDTO response = new HorarioResponseDTO();
        response.setIdHorario(horario.getIdHorario());
        response.setData(horario.getData());
        response.setHoraInicio(horario.getHoraInicio());
        response.setHoraFim(horario.getHoraFim());
        response.setDisponivel(horario.isDisponivel());

        // Mapeia o Resumo da Empresa
        if (horario.getEmpresa() != null) {
            EmpresaResumoDTO resumo = new EmpresaResumoDTO();
            resumo.setIdEmpresa(horario.getEmpresa().getIdEmpresa());
            resumo.setDocumento(horario.getEmpresa().getDocumento());
            resumo.setRazaoSocial(horario.getEmpresa().getRazaoSocial());
            resumo.setNomeFantasia(horario.getEmpresa().getNomeFantasia());
            resumo.setRepresentante(horario.getEmpresa().getRepresentante());
            resumo.setLogo(horario.getEmpresa().getLogo());
            response.setEmpresaResumoDTO(resumo);
        }
        if(horario.getAgenda() != null){
            AgendaResponseDTO resumo = new AgendaResponseDTO();
            resumo.setIdAgenda(horario.getAgenda().getIdAgenda());
            resumo.setDataInicio(horario.getAgenda().getDataInicio());
            resumo.setDataFinal(horario.getAgenda().getDataFinal());
            resumo.setHoraAbertura(horario.getAgenda().getHoraAbertura());
            resumo.setHoraAlmoco(horario.getAgenda().getHoraAlmoco());
            resumo.setHoraRetornoAlmoco(horario.getAgenda().getHoraRetornoAlmoco());
            resumo.setHoraFechamento(horario.getAgenda().getHoraFechamento());
            resumo.setDuracaoSlot(horario.getAgenda().getDuracaoSlot());
            resumo.setDiasSemana(horario.getAgenda().getDiasSemana());
            resumo.setCriadoEm(horario.getAgenda().getCriadoEm());
            resumo.setAtualizadoEm(horario.getAgenda().getAtualizadoEm());
            resumo.setEmpresa(response.getEmpresaResumoDTO());
            response.setAgendaResponseDTO(resumo);
        }

        return response;
    }

    @Transactional
    public List<Horario> cadastrarHorarios(Agenda agenda, Integer idEmpresa) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseGet(() -> empresaRepository.findById(2).orElseThrow());

        List<Horario> horariosGerados = new ArrayList<>();

        int intervalo = agenda.getDuracaoSlot(); // ex: 30 minutos

        // 🔁 Loop de datas
        for (
                LocalDate dataAtual = agenda.getDataInicio();
                !dataAtual.isAfter(agenda.getDataFinal());
                dataAtual = dataAtual.plusDays(1)
        ) {

            LocalTime horarioAtual = agenda.getHoraAbertura();

            // 🔁 Loop de horários do dia
            while (horarioAtual.isBefore(agenda.getHoraFechamento())) {

                boolean noAlmoco =
                        (horarioAtual.equals(agenda.getHoraAlmoco()) || horarioAtual.isAfter(agenda.getHoraAlmoco()))
                                && horarioAtual.isBefore(agenda.getHoraRetornoAlmoco());

                if (!noAlmoco) {
                    Horario horario = new Horario();
                    horario.setAgenda(agenda);
                    horario.setEmpresa(empresa);
                    horario.setDisponivel(true);

                    horario.setData(dataAtual); // 👈 aqui está a chave
                    horario.setHoraInicio(horarioAtual);
                    horario.setHoraFim(horarioAtual.plusMinutes(intervalo));

                    horariosGerados.add(horario);
                }

                horarioAtual = horarioAtual.plusMinutes(intervalo);
            }
        }

        return horarioRepository.saveAll(horariosGerados);
    }

    public List<HorarioResponseDTO> consultarHorarios(){
        List<Horario> horarios = horarioRepository.findAll();

        // Transforma cada Agenda da lista em um AgendaResponseDTO
        return horarios.stream()
                .map(this::converterParaResponseDTO) // Reaproveita o metodo que já criamos
                .toList(); // No Java 16+ ou 17 (padrão em 2026), usa-se .toList()
    }

    public List<HorarioResponseDTO> consultarHorariosPorDisponibilidade(){
        List<Horario> horariosDisponiveis = horarioRepository.findByDisponivel(true);

        return horariosDisponiveis.stream()
                .map(this::converterParaResponseDTO) // Reaproveita o metodo que já criamos
                .toList(); // No Java 16+ ou 17 (padrão em 2026), usa-se .toList()
    }

    public List<HorarioResponseDTO> consultarHorariosPorIndisponibilidade(){
        List<Horario> horariosIndisponiveis = horarioRepository.findByDisponivel(false);

        return horariosIndisponiveis.stream()
                .map(this::converterParaResponseDTO) // Reaproveita o metodo que já criamos
                .toList(); // No Java 16+ ou 17 (padrão em 2026), usa-se .toList()
    }

    public List<HorarioResponseDTO> consultarHorariosPorDataEDisponibilidade(LocalDate data){
        List<Horario> horariosDisponiveis = horarioRepository.findByDataAndDisponivel(data, true);

        return horariosDisponiveis.stream()
                .map(this::converterParaResponseDTO) // Reaproveita o metodo que já criamos
                .toList(); // No Java 16+ ou 17 (padrão em 2026), usa-se .toList()
    }

    public HorarioResponseDTO consultarHorarioPorId(int id){
        Horario horarioExistente = horarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Horario não encontrado!!"));

        return converterParaResponseDTO(horarioExistente);
    }

    @Transactional
    public HorarioResponseDTO alterarDisponibilidadePorId(int id){
        Horario horarioExistente = horarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Horario não encontrado!!"));

        if (horarioExistente.isDisponivel()){
            horarioExistente.setDisponivel(false);
        }

        horarioExistente = horarioRepository.save(horarioExistente);

        return converterParaResponseDTO(horarioExistente);
    }

    public HorarioResponseDTO alterarDisponibilidadePorDataEHora(LocalDate data, LocalTime horaInicio){
        Horario horarioExistente = horarioRepository.findByDataAndHoraInicio(data, horaInicio)
                .orElseThrow(() -> new RuntimeException("Horario não encontrado!!"));

        if (horarioExistente.isDisponivel()){
            horarioExistente.setDisponivel(false);
        }

        horarioExistente = horarioRepository.save(horarioExistente);

        return converterParaResponseDTO(horarioExistente);
    }

    @Transactional
    public List<HorarioResponseDTO> bloquearHorariosDoAgendamento(LocalDateTime dataInicio, LocalDateTime dataFim) {

        LocalDate data = dataInicio.toLocalDate();
        LocalTime horaInicio = dataInicio.toLocalTime();
        LocalTime horaFim = dataFim.toLocalTime();

        List<Horario> horarios = horarioRepository
                .findHorariosNoIntervalo(data, horaInicio, horaFim);

        if (horarios.isEmpty()) {
            throw new RuntimeException("Nenhum horário encontrado para o intervalo informado");
        }

        for (Horario h : horarios) {
            h.setDisponivel(false);
        }

        horarioRepository.saveAll(horarios);

        List<Horario> horariosDisponiveis = horarioRepository.findByDataAndDisponivel(data, false);

        return horariosDisponiveis.stream()
                .map(this::converterParaResponseDTO) // Reaproveita o metodo que já criamos
                .toList(); // No Java 16+ ou 17 (padrão em 2026), usa-se .toList()
    }

}