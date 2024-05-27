package com.healthcare.beneficiary.application.rest.controllers;

import com.healthcare.beneficiary.domain.dtos.BeneficiarioDTO;
import com.healthcare.beneficiary.domain.dtos.DocumentoDTO;
import com.healthcare.beneficiary.domain.entities.Beneficiario;
import com.healthcare.beneficiary.domain.entities.Documento;
import com.healthcare.beneficiary.domain.services.BeneficiarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("beneficiario")
public class BeneficiarioController {

    @Autowired
    private BeneficiarioService beneficiarioService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<BeneficiarioDTO> save(@RequestBody BeneficiarioDTO beneficiarioDTO) {

        Beneficiario beneficiarioCriado =
                beneficiarioService.saveOrUpdate(modelMapper.map(beneficiarioDTO, Beneficiario.class));

        return new ResponseEntity<BeneficiarioDTO>(modelMapper.map(beneficiarioCriado, BeneficiarioDTO.class), HttpStatus.CREATED);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BeneficiarioDTO> update(@PathVariable(name = "id") int id, @RequestBody BeneficiarioDTO beneficiarioDTO) {

        Beneficiario update = beneficiarioService.prepareForUpdate(id, beneficiarioDTO);

        Optional<Beneficiario> dto = beneficiarioService.findById(id);

        if (dto.isPresent()){
            Beneficiario toUpdate = modelMapper.map(dto.get(), Beneficiario.class);
            Beneficiario beneficiarioUpdated =
                    beneficiarioService.saveOrUpdate(update);
            return new ResponseEntity<BeneficiarioDTO>(modelMapper.map(beneficiarioUpdated, BeneficiarioDTO.class), HttpStatus.NO_CONTENT);
        }
        else {
            return notFound().build();
        }

    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Void> deleteById(@PathVariable(name = "id") int id) {

        beneficiarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<BeneficiarioDTO> findById(@PathVariable(name = "id") int beneficiarioId) {

        Optional<Beneficiario> beneficiarioOptional = beneficiarioService.findById(beneficiarioId);
        return beneficiarioOptional.map(beneficiario -> modelMapper.map(beneficiario, BeneficiarioDTO.class))
                .map(ResponseEntity::ok).orElseGet(() -> notFound().build());

    }

    @GetMapping("/beneficiarios/{id}/docs")
    private ResponseEntity<List<DocumentoDTO>> findDocumentsById(
            @PathVariable(name = "id") int beneficiarioId) {

        Optional<List<Documento>> beneficiarioOptional = beneficiarioService.findDocumentsById(beneficiarioId);
        if (beneficiarioOptional.isPresent()) {
            List<DocumentoDTO> dtos =
                    beneficiarioOptional.get()
                    .stream()
                    .map(documento -> modelMapper.map(documento, DocumentoDTO.class))
                    .toList();

            return ResponseEntity.ok(dtos);
        }
        return notFound().build();

    }

    @GetMapping("/beneficiarios")
    private List<BeneficiarioDTO> findAll() {

        List<Beneficiario> beneficiarios = beneficiarioService.findAll();
        return beneficiarios.stream().map(beneficiario -> modelMapper.map(beneficiario, BeneficiarioDTO.class))
                .toList();

    }

}
