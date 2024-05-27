package com.healthcare.beneficiary.domain.services;

import com.healthcare.beneficiary.domain.entities.Documento;
import com.healthcare.beneficiary.domain.repositories.IDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentoService {

    @Autowired
    private IDocumentoRepository documentoRepository;

    @Transactional(rollbackFor = Exception.class)
    public List<Documento> save(List<Documento> documentos) {
        return documentoRepository.saveAll(documentos);
    }
}
