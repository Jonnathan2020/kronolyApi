package com.kronoly.Service;

import com.kronoly.DTO.ServicoCreateDTO;
import com.kronoly.DTO.ServicoResumoDTO;
import com.kronoly.DTO.ServicoUpdateDTO;
import com.kronoly.Entity.Agendamento;
import com.kronoly.Entity.Servico;
import com.kronoly.Repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public ServicoResumoDTO cadastrarServico(ServicoCreateDTO servicoCreateDTO){

        Servico servico = new Servico();

        servico.setDescricao(servicoCreateDTO.getDescricao());
        servico.setTempoEstimado(servicoCreateDTO.getTempoEstimado());
        servico.setValorCusto(servicoCreateDTO.getValorCusto());
        servico.setValorServico(servicoCreateDTO.getValorServico());

        return new ServicoResumoDTO(servicoRepository.save(servico));
    }

    public List<ServicoResumoDTO> consultarServicos(){
        List<Servico> servicos = servicoRepository.findAll();

        if (servicos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum serviço encontrado!");
        }

        return servicos.stream().map(servico -> new ServicoResumoDTO(
                servico.getIdServico(),
                servico.getDescricao(),
                servico.getTempoEstimado(),
                servico.getValorCusto(),
                servico.getValorServico()
        )
        ).collect(Collectors.toList());
    }

    public ServicoResumoDTO consultarServicoPorId(int id){
        Servico servicoExistente = servicoRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Nenhum serviço encontrado!"));

        return new ServicoResumoDTO(servicoExistente);
    }

    public List<ServicoResumoDTO> consultarServicoPorDescricao(String descricao){
        List<Servico> servicoExistente = servicoRepository.findByDescricaoIgnoreCaseContaining(descricao);

        if (servicoExistente.isEmpty()) {
            throw new IllegalArgumentException("Nenhum serviço encontrado!");
        }

        return servicoExistente.stream().map(servico -> new ServicoResumoDTO(
                        servico.getIdServico(),
                        servico.getDescricao(),
                        servico.getTempoEstimado(),
                        servico.getValorCusto(),
                        servico.getValorServico()
                )
        ).collect(Collectors.toList());
    }

    public ServicoResumoDTO alterarServico(int idServico, ServicoUpdateDTO servicoUpdateDTO){
        Servico servicoExistente = servicoRepository.findById(idServico)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado!!!"));

        if (servicoUpdateDTO.getDescricao() != null){
            servicoExistente.setDescricao(servicoUpdateDTO.getDescricao());
        }
        if(servicoUpdateDTO.getTempoEstimado() != null){
            servicoExistente.setTempoEstimado(servicoUpdateDTO.getTempoEstimado());
        }
        if(servicoUpdateDTO.getValorCusto() != 0){
            servicoExistente.setValorCusto(servicoUpdateDTO.getValorCusto());
        }
        if(servicoUpdateDTO.getValorServico() !=0){
            servicoExistente.setValorServico(servicoUpdateDTO.getValorServico());
        }

        servicoRepository.save(servicoExistente);
        return new ServicoResumoDTO(servicoExistente);
    }

    public void deleteServico(int idServico){
        servicoRepository.deleteById(idServico);
    }
}
