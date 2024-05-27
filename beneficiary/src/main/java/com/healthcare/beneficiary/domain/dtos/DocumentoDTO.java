package com.healthcare.beneficiary.domain.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoDTO {

    private int id;
    private String tipoDocumento;
    private String descricao;
    private String dataInclusao;
    private String dataAtualizacao;

}
