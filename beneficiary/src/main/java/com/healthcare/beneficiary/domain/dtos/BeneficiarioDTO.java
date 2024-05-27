package com.healthcare.beneficiary.domain.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiarioDTO {
    private int id;
    private String nome;
    private String telefone;
    private String dataNascimento;
    private String dataInclusao;
    private String dataAtualizacao;
    private List<DocumentoDTO> documentos;
}
