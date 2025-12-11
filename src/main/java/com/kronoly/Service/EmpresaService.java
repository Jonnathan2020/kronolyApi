package com.kronoly.Service;

import com.kronoly.DTO.EmpresaCreateDTO;
import com.kronoly.DTO.EmpresaUpdateDTO;
import com.kronoly.Entity.Empresa;
import com.kronoly.Entity.Endereco;
import com.kronoly.Repository.EmpresaRepository;
import com.kronoly.Repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {
    @Autowired
    public EmpresaRepository empresaRepository;

    @Autowired
    public EnderecoRepository enderecoRepository;

    public Empresa cadastrarEmpresa(EmpresaCreateDTO empresaCreateDTO){
        Empresa empresa = new Empresa();

        empresa.setDocumento(empresaCreateDTO.getDocumento());
        empresa.setRazaoSocial(empresaCreateDTO.getRazaoSocial());
        empresa.setNomeFantasia(empresaCreateDTO.getNomeFantasia());
        empresa.setRepresentante(empresaCreateDTO.getRepresentante());
        empresa.setLogo(empresaCreateDTO.getLogo());
        empresa.setStatusEmpresaEnum(empresaCreateDTO.getStatusEmpresaEnum());

        // Criação do endereço com base nos dados do DTO
        Endereco endereco = new Endereco();
        endereco.setLogradouro(empresaCreateDTO.getLogradouro());
        endereco.setNumero(empresaCreateDTO.getNumero());
        endereco.setBairro(empresaCreateDTO.getBairro());
        endereco.setCidade(empresaCreateDTO.getCidade());
        endereco.setUf(empresaCreateDTO.getUf());
        endereco.setCep(empresaCreateDTO.getCep());
        endereco.setComplemento(empresaCreateDTO.getComplemento());
        endereco = enderecoRepository.save(endereco);

        empresa.setEndereco(endereco);

        return empresaRepository.save(empresa);
    }

    public List<Empresa> consultarEmpresas(){
        return empresaRepository.findAll();
    }

    public Empresa consultarEmpresaPorId(int idEmpresa){
        Empresa empresaEspecifica = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada!!"));

                return empresaEspecifica;
    }

    public List<Empresa> consultarEmpresaPorNomeFantasia(String nomeFantasia){
        return empresaRepository.findByNomeFantasiaIgnoreCaseContaining(nomeFantasia);
    }

    public List<Empresa> consultarEmpresaPorRazaoSocial(String razaoSocial){
        return empresaRepository.findByRazaoSocialIgnoreCaseContaining(razaoSocial);
    }

    public List<Empresa> consultarEmpresaPorDocumento(String documento){
        return empresaRepository.findByDocumentoIgnoreCaseContaining(documento);
    }

    public Empresa alterarEmpresa(int idEmpresa, EmpresaUpdateDTO empresaUpdateDTO){
        Empresa empresaExistente = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        if(empresaUpdateDTO.getDocumento() != null){
            empresaExistente.setDocumento(empresaUpdateDTO.getDocumento());
        }

        if(empresaUpdateDTO.getRazaoSocial() != null){
            empresaExistente.setRazaoSocial(empresaUpdateDTO.getRazaoSocial());
        }

        if(empresaUpdateDTO.getNomeFantasia() != null){
            empresaExistente.setNomeFantasia(empresaUpdateDTO.getNomeFantasia());
        }

        if(empresaUpdateDTO.getRepresentante() !=null){
            empresaExistente.setRepresentante(empresaUpdateDTO.getRepresentante());
        }

        if(empresaUpdateDTO.getLogo() !=null){
            empresaExistente.setLogo(empresaUpdateDTO.getLogo());
        }

        if(empresaUpdateDTO.getStatusEmpresaEnum() != null){
            empresaExistente.setStatusEmpresaEnum(empresaUpdateDTO.getStatusEmpresaEnum());
        }

        return empresaRepository.save(empresaExistente);

    }

    public void delete(int idEmpresa){
        empresaRepository.deleteById(idEmpresa);
    }


}
