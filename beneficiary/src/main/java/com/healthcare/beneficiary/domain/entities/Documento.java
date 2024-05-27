package com.healthcare.beneficiary.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documento")
public class Documento {

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private int id;
    private String tipoDocumento;
    private String descricao;
    private String dataInclusao;
    private String dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "beneficiario_id", nullable = false)
    private Beneficiario beneficiario;

}
