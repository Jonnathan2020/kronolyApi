package com.kronoly.Service;

import com.kronoly.DTO.AgendaCreateDTO;
import com.kronoly.DTO.AgendaUpdateDTO;
import com.kronoly.Entity.Agenda;
import com.kronoly.Entity.Empresa;
import com.kronoly.Repository.AgendaRepository;
import com.kronoly.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    public Agenda cadastrarAgenda(AgendaCreateDTO agendaCreateDTO){

        Empresa empresa = empresaRepository.findById(agendaCreateDTO.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Agenda agenda = new Agenda();

        agenda.setStatusAgendaEnum(agendaCreateDTO.getStatusAgendaEnum());
        agenda.setHoraAbertura(agendaCreateDTO.getHoraAbertura());
        agenda.setHoraAlmoco(agendaCreateDTO.getHoraAlmoco());
        agenda.setHoraFechamento(agendaCreateDTO.getHoraFechamento());
        agenda.setEmpresa(empresa);
        return agendaRepository.save(agenda);
    }

    public List<Agenda> consultarAgendas(){
        return agendaRepository.findAll();
    }

    public Agenda consultarAgendaPorId(int idAgenda){
        Agenda agendaEspecifica = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada!!"));

        return agendaEspecifica;
    }

    public List<Agenda> consultarAgendasPorEmpresa(int idEmpresa) {
        // Primeiro, verifica se a empresa existe
        empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        // Busca todas as agendas dessa empresa
        List<Agenda> agendas = agendaRepository.findByEmpresa_idEmpresa(idEmpresa);

        if (agendas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma agenda encontrada para essa empresa");
        }

        return agendas;
    }

    public Agenda alterarAgenda(int idAgenda, AgendaUpdateDTO agendaUpdateDTO){
        Agenda agendaExistente = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada!!!"));

        if (agendaUpdateDTO.getHoraAbertura() != null){
            agendaExistente.setHoraAbertura(agendaUpdateDTO.getHoraAbertura());
        }

        if(agendaUpdateDTO.getStatusAgendaEnum() != null){
            agendaExistente.setStatusAgendaEnum(agendaUpdateDTO.getStatusAgendaEnum());
        }

        if(agendaUpdateDTO.getHoraAlmoco() != null){
            agendaExistente.setHoraAlmoco(agendaUpdateDTO.getHoraAlmoco());
        }

        if(agendaUpdateDTO.getHoraFechamento() != null){
            agendaUpdateDTO.setHoraFechamento(agendaUpdateDTO.getHoraFechamento());
        }

        return agendaRepository.save(agendaExistente);
    }

    public void delete(int idAgenda){
        agendaRepository.deleteById(idAgenda);
    }
}
