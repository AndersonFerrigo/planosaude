package com.healthcare.beneficiary.domain.services;

import com.healthcare.beneficiary.domain.dtos.BeneficiarioDTO;
import com.healthcare.beneficiary.domain.dtos.DocumentoDTO;
import com.healthcare.beneficiary.domain.entities.Beneficiario;
import com.healthcare.beneficiary.domain.entities.Documento;
import com.healthcare.beneficiary.domain.repositories.IBeneficiarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
public class BeneficiarioService {

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private IBeneficiarioRepository iBeneficiarioRepository;

    @Autowired
    private ModelMapper mapper;

    public Beneficiario prepareForUpdate(int id, BeneficiarioDTO beneficiarioDTO){

        Optional<Beneficiario> beneficiarioOptional = findById(id);

        if (beneficiarioOptional.isPresent()){

            beneficiarioOptional.get().setNome(beneficiarioDTO.getNome());
            beneficiarioOptional.get().setTelefone(beneficiarioDTO.getTelefone());
            beneficiarioOptional.get().setDataNascimento(beneficiarioDTO.getDataNascimento());
            beneficiarioOptional.get().setDataInclusao(beneficiarioDTO.getDataInclusao());
            beneficiarioOptional.get().setDataAtualizacao(beneficiarioDTO.getDataAtualizacao());

            beneficiarioDTO.getDocumentos().forEach(update ->{
                beneficiarioOptional.get().getDocumentos()
                        .forEach(rec ->{
                            rec.setTipoDocumento(update.getTipoDocumento());
                            rec.setDescricao(update.getDescricao());
                            rec.setDataInclusao(update.getDataInclusao());
                            rec.setDataAtualizacao(update.getDataAtualizacao());
                        });
            });

        }

        return beneficiarioOptional.get();

    }



    @Transactional
    public Beneficiario saveOrUpdate(Beneficiario beneficiario) {
        Beneficiario savedBeneficiario = iBeneficiarioRepository.save(beneficiario);
        beneficiario.getDocumentos().forEach(documento -> documento.setBeneficiario(savedBeneficiario));
        List<Documento> documentos = documentoService.save(beneficiario.getDocumentos());
        savedBeneficiario.setDocumentos(documentos);
        return savedBeneficiario;
    }


    @Transactional(rollbackFor = Exception.class)
    public Beneficiario deleteById(int id) {
        Optional<Beneficiario> beneficiarioOptional = findById(id);
        if (beneficiarioOptional.isPresent()) {
            Beneficiario beneficiario = beneficiarioOptional.get();
            iBeneficiarioRepository.deleteById(beneficiario.getId());
            return beneficiario;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Beneficiario> findAll() {
        return iBeneficiarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Beneficiario> findById(int id) {
        return iBeneficiarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<List<Documento>> findDocumentsById(int id) {
        Optional<Beneficiario> beneficiarioOptional = iBeneficiarioRepository.findById(id);
        return beneficiarioOptional.map(Beneficiario::getDocumentos);
    }

}