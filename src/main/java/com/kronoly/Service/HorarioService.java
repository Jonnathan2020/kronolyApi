package com.kronoly.Service;

import com.kronoly.DTO.AgendaCreateDTO;
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

    @Transactional
    public List<Horario> cadastrarHorarios(Agenda agenda, Integer idEmpresa) {
        // 1. Busca as entidades necessárias
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseGet(() -> empresaRepository.findById(2).orElseThrow());

        List<Horario> horariosGerados = new ArrayList<>();
        LocalTime horarioAtual = agenda.getHoraAbertura();
        int intervalo = agenda.getDuracaoSlot(); // 30 minutos

        // 2. Loop para gerar os slots
        while (horarioAtual.isBefore(agenda.getHoraFechamento())) {

            // Regra: Não criar horários durante o almoço
            boolean noAlmoco = (horarioAtual.equals(agenda.getHoraAlmoco()) || horarioAtual.isAfter(agenda.getHoraAlmoco()))
                    && horarioAtual.isBefore(agenda.getHoraRetornoAlmoco());

            if (!noAlmoco) {
                Horario horario = new Horario();
                horario.setAgenda(agenda);
                horario.setEmpresa(empresa);
                horario.setDisponivel(true);

                // Define o início e o fim do slot (ajuste os nomes dos campos se necessário)
                horario.setData(LocalDate.now());
                horario.setHoraInicio(horarioAtual);
                horario.setHoraFim(horarioAtual.plusMinutes(intervalo));
                horariosGerados.add(horario);
            }

            // Incrementa o horário atual para o próximo slot
            horarioAtual = horarioAtual.plusMinutes(intervalo);
        }

        // 3. Salva todos os horários gerados e retorna a lista
        return horarioRepository.saveAll(horariosGerados);
    }

    public List<Horario> consultarHorarios(){
        return horarioRepository.findAll();
    }

    public Horario consultarHorarioPorId(int id){
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Horario não encontrado!!"));

        return horario;
    }
}